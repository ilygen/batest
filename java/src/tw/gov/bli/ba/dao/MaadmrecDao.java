package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 行政支援記錄檔 (<code>MAADMERC</code>)
 * 
 * @author swim
 */
public interface MaadmrecDao {
    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMERC</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param idNo 身份證號
     * @return 內含 <code>MAADMERC</code> 物件的 List
     */
    public List<Maadmrec> selectExecutiveSupportQueryListBy(String apNo, String idNo, String issuYm);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMERC</code>) 資料清單且行政支援記錄未銷案
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param proDate 承辦日期
     * @param seqNo 序號
     * @param letterType 處理函別代碼
     * @return 內含 <code>MAADMERC</code> 物件的 List
     */
    public List<Maadmrec> selectExecutiveSupportCloseListBy(String apNo, String issuYm, String proDate, String seqNo, String letterType);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMERC</code>) 序號
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param proDate 承辦日期
     * @param letterType 處理函別代碼
     * @return 內含 <code>序號</code> 的String
     */
    public String selectSeqNoBy(String apNo, String issuYm, String proDate, String letterType);

    /**
     * 新增 行政支援記錄檔(<code>MAADMERC</code>) 資料
     * 
     * @param MAADMREC 行政支援記錄檔
     */
    public BigDecimal insertData(Maadmrec maadmrec);

    /**
     * 更新 行政支援記錄檔(<code>MAADMERC</code>) 資料
     * 
     * @param HashMap
     */
    public void updateData(Maadmrec maadmrec);

    /**
     * 更新 行政支援記錄檔(<code>MAADMERC</code>) closeDate資料
     * 
     * @param HashMap
     */
    public void updateDataClosedate(Maadmrec maadmrec);

    /**
     * 刪除 行政支援記錄檔(<code>MAADMERC</code>) 資料
     * 
     * @param letterType 處理函別代碼
     * @param proDate 承辦日期
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteData(ExecutiveSupportDataCase caseData);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk1(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk2(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk3(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk4(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk5(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk6(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk1K(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk2K(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk3K(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk4K(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk5K(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk6K(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 交查函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk1S(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 不給付函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk2S(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 補件通知函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk3S(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 照會函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk4S(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 其他簽函資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk5S(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
     * 
     * @param apNo 受理編號
     * @return <code>Maadmrec</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectLetterTypeMk6S(String apNo);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 救濟種類資料
     * 
     * @param reliefKind 救濟種類
     * @return <code>Maadmrec</code> 物件
     */
    public String selectLetterTypeMk6Kind(String reliefKind);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 行政救濟辦理情形資料
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Maadmrec</code> 物件
     */
    public String selectLetterTypeMk6Stat(String reliefKind, String reliefStat);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 年金給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Maadmrec getOldAgeReviewRpt01AnnuityPayDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) Q1 for 給付審核(判斷「人工審核結果」radio button用)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Integer</code> 物件
     */
    public Integer selectQ1ForReview(String apNo, String issuYm);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) Q2 for 給付審核(判斷「人工審核結果」radio button用)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Integer</code> 物件
     */
    public Integer selectQ2ForReview(String apNo, String issuYm);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Maadmrec selectSurvivorReviewRpt01AnnuityPayDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Maadmrec getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>MAADMREC</code>) 資料 for 審核清單明細資料
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Maadmrec> selectMaadmrecDataBy(String apNo);

    /**
     * 新增 行政支援記錄檔 (<code>MAADMREC</code>) 資料
     * 
     * @param MAADMREC 行政支援記錄檔
     * @return
     */
    public void insertDataForMonthlyRpt31(final List<Maadmrec> maadmrecList);

    /**
     * 依傳入條件取得 行政支援記錄檔 (<code>MAADMREC</code>) MAADMRECID
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ISSUYM 核 定年月
     * @param LETTERTYPE 函別
     * @param PRODATE 承辦日期
     * @return BigDecimal
     */
    public BigDecimal selectMaAdmRecIdForMonthlyRpt31(String apNo, String seqNo, String issuYm, String LetterType, String proDate);

    /**
     * 更新 行政支援記錄檔(<code>MAADMERC</code>) closeDate 資料
     * 
     * @param
     */
    public void updateCloseDateForMonthlyRpt31(Maadmrec maadmrec);

    /**
     * 更新 行政支援記錄檔(<code>MAADMERC</code>) FOR 結案狀態變更作業
     * 
     * @param HashMap
     */
    public void updateDataForCloseStatusAlteration(String apNo, String updUser);
}
