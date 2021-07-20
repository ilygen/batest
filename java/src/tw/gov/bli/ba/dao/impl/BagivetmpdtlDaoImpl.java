package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BagivetmpdtlDao;
import tw.gov.bli.ba.domain.Bagivetmpdtl;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>)
 * 
 * @author Goston
 */
@DaoTable("BAGIVETMPDTL")
public class BagivetmpdtlDaoImpl extends SqlMapClientDaoSupport implements BagivetmpdtlDao {

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 所輸入的 給付別 及 核定年月 所含有之 出帳類別 List
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<String> getMonthlyRpt07TaTyp2List(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRpt07TaTyp2List", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 所輸入的 給付別 及 核定年月 所含有之 出帳類別 List
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<String> getMonthlyRptK07TaTyp2List(String payCode, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        map.put("paySeqNo", paySeqNo);
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptK07TaTyp2List", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 所輸入的 給付別 及 核定年月 所含有之 出帳類別 List
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<String> getMonthlyRptS07TaTyp2List(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptS07TaTyp2List", map);
    }    

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRpt07HeaderDataBy(String payCode, String issuYm, String taTyp2, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRpt07HeaderDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRpt07mFileNameBy(String payCode, String issuYm, String taTyp2, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRpt07mFileNameBy", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRptK07HeaderDataBy(String payCode, String issuYm, String taTyp2, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("paySeqNo", paySeqNo);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptK07HeaderDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRptS07HeaderDataBy(String payCode, String issuYm, String taTyp2, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptS07HeaderDataBy", map);
    }    

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRpt07MiddleDataBy(String payCode, String issuYm, String taTyp2, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRpt07MiddleDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRptK07MiddleDataBy(String payCode, String issuYm, String taTyp2, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("paySeqNo", paySeqNo);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptK07MiddleDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRptS07MiddleDataBy(String payCode, String issuYm, String taTyp2, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptS07MiddleDataBy", map);
    }    

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRpt07FooterDataBy(String payCode, String issuYm, String taTyp2, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRpt07FooterDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRptK07FooterDataBy(String payCode, String issuYm, String taTyp2, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        map.put("paySeqNo", paySeqNo);
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptK07FooterDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,TATYP2")
    public List<Bagivetmpdtl> getMonthlyRptS07FooterDataBy(String payCode, String issuYm, String taTyp2, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("taTyp2", taTyp2);
        map.put("chkDate", DateUtility.changeDateType(chkDate));
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMonthlyRptS07FooterDataBy", map);
    }    
    /**
	 * 依傳入條件取得該核定日期、核定年月、給付別的對應下載清單
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @param paySeqNo 欄位值(皆預設為1第一次出媒體)
	 * @return 
	 */
    public List<Bagivetmpdtl> getMedaiFileOnlineDownloadDataBy(String payCode, String issuYm, String chkDate,String paySeqNo) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("paySeqNo", paySeqNo);
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return getSqlMapClientTemplate().queryForList("BAGIVETMPDTL.getMedaiFileOnlineDownloadDataBy", map);
    	
    }
}
