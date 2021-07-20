package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.NbappbaseDao;
import tw.gov.bli.ba.domain.Nbappbase;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 國保給付主檔 (<code>NBAPPBASE</code>)
 * 
 * @author Rickychi
 */
@DaoTable("NBAPPBASE")
public class NbappbaseDaoImpl extends SqlMapClientDaoSupport implements NbappbaseDao {
    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料 for 給付審核
     * 
     * @param evtIds 事故者社福識別碼
     * @return 內含 <code>Nbappbase</code> 物件的 List
     */
    @DaoFieldList("EVTIDS")
    public List<Nbappbase> selectNpPayDataBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIds", evtIds);
        return getSqlMapClientTemplate().queryForList("NBAPPBASE.selectNpPayDataBy", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付記錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    @DaoFieldList("EVTIDS")
    public List<Nbappbase> getOldAgeReviewRpt01NpPayListBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("evtIds", evtIds);

        return getSqlMapClientTemplate().queryForList("NBAPPBASE.getOldAgeReviewRpt01NpPayListBy", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付記錄資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    @DaoFieldList("EVTIDS")
    public List<Nbappbase> selectSurvivorReviewRpt01NpPayListBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("evtIds", evtIds);

        return getSqlMapClientTemplate().queryForList("NBAPPBASE.selectSurvivorReviewRpt01NpPayListBy", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保喪葬給付記錄資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    @DaoFieldList("EVTIDS")
    public List<Nbappbase> selectSurvivorReviewRpt01NpDidePayListBy(String evtIds, String apNoTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("evtIds", evtIds);
        if ("C".equals(apNoTyp)) {
            map.put("apNoTyp", "C");
        }

        if ("D".equals(apNoTyp)) {
            map.put("apNoTyp", "D");
        }

        return getSqlMapClientTemplate().queryForList("NBAPPBASE.selectSurvivorReviewRpt01NpDidePayListBy", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    @DaoFieldList("EVTIDS")
    public List<Nbappbase> getDisableReviewRpt01NpPayListBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("evtIds", evtIds);

        return getSqlMapClientTemplate().queryForList("NBAPPBASE.getDisableReviewRpt01NpPayListBy", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保身障給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    @DaoFieldList("EVTIDS")
    public List<Nbappbase> getDisableReviewRpt01NpDisPayListBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("evtIds", evtIds);

        return getSqlMapClientTemplate().queryForList("NBAPPBASE.selectDisableReviewRpt01NpDisPayListBy", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) LABMERGE
     * 
     * @param apNo
     * @return
     */
    @DaoFieldList("APNO")
    public String selectLabmergeByApNo(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (String) getSqlMapClientTemplate().queryForObject("NBAPPBASE.selectLabmergeByApNo", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 國保資料 FOR 失能年金給付查詢
     * 
     * @param apNo
     * @return
     */
    @DaoFieldList("APNO")
    public List<Nbappbase> selectPaymentQueryDisabledNpDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("NBAPPBASE.selectPaymentQueryDisabledNpDataBy", map);
    }

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 國保資料 FOR 失能年金給付受理/審核清單 國保資料 payKind=38
     * 
     * @param apNo
     * @return
     */
    @DaoFieldList("APNO")
    public Nbappbase selectDisabledReviewRpt01NpData38By(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Nbappbase) getSqlMapClientTemplate().queryForObject("NBAPPBASE.selectDisabledReviewRpt01NpData38By", map);
    }
    
    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 國保資料 FOR 失能年金給付受理/審核清單 國保資料 payKind=36
     * 
     * @param apNo
     * @return
     */
    @DaoFieldList("APNO")
    public Nbappbase selectDisabledReviewRpt01NpData36By(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Nbappbase) getSqlMapClientTemplate().queryForObject("NBAPPBASE.selectDisabledReviewRpt01NpData36By", map);
    }
    
    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料 for 失能年金給付受理
     * 
     * @param apNo 國寶受理編號
     * @return 內含 <code>Nbappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Nbappbase> selectDataBy(String apNo,String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("NBAPPBASE.selectDataBy", map);
    }
}
