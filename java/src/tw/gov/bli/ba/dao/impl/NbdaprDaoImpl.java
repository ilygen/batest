package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.NbdaprDao;
import tw.gov.bli.ba.domain.Nbdapr;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 國保給付核定檔 (<code>NBDAPR</code>)
 * 
 * @author Rickychi
 */
@DaoTable("NBDAPR")
public class NbdaprDaoImpl extends SqlMapClientDaoSupport implements NbdaprDao {
    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 申請國保給付記錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Nbdapr getOldAgeReviewRpt01NpPayDetailDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);

        return (Nbdapr) getSqlMapClientTemplate().queryForObject("NBDAPR.getOldAgeReviewRpt01NpPayDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 申請國保給付記錄資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Nbdapr selectSurvivorReviewRpt01NpPayDetailDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);
        return (Nbdapr) getSqlMapClientTemplate().queryForObject("NBDAPR.selectSurvivorReviewRpt01NpPayDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 申請國保給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Nbdapr getDisableReviewRpt01NpPayDetailDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);

        return (Nbdapr) getSqlMapClientTemplate().queryForObject("NBDAPR.getDisableReviewRpt01NpPayDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 國保核定資料 FOR 失能年金給付查詢
     * 
     * @param apNo 受理編號
     * @param qryCond 查詢條件
     * @param startYm 開始年月
     * @param endYm 結束年月
     * @return
     */
    @DaoFieldList("APNO,QRYCOND,STARTYM,ENDYM")
    public List<Nbdapr> selectPaymentQueryDisabledNpIssuDataBy(String apNo, String qryCond, String startYm, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("qryCond", qryCond);
        map.put("startYm", startYm);
        map.put("endYm", endYm);

        return getSqlMapClientTemplate().queryForList("NBDAPR.selectPaymentQueryDisabledNpIssuDataBy", map);
    }
    
    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 國保核定資料 FOR 失能年金給付受理/審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Nbdapr> selectDisabledReviewRpt01NpDataList36By(String apNo, String issuYm){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("NBDAPR.selectDisabledReviewRpt01NpDataList36By", map);
    }
    
    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 國保核定資料 FOR 失能年金給付受理/審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Nbdapr> selectDisabledReviewRpt01NpDataList38By(String mapNo, String apNo, String issuYm){
        HashMap<String, String> map = new HashMap<String, String>();
       
        map.put("mapNo", mapNo);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("NBDAPR.selectDisabledReviewRpt01NpDataList38By", map);
    }
}
