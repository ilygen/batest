package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BebmsaDao;
import tw.gov.bli.ba.domain.Bebmsa;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 現金給付參考檔 (<code>BEBMSA</code>) <br>
 * 
 * @author Goston
 */
@DaoTable("BEBMSA")
public class BebmsaDaoImpl extends SqlMapClientDaoSupport implements BebmsaDao {

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getOldAgeReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getOldAgeReviewRpt01OncePayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失能給付記錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getOldAgeReviewRpt01DisablePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getOldAgeReviewRpt01DisablePayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getOldAgeReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getOldAgeReviewRpt01DiePayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getOldAgeReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate, String evtJobDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(evtJobDate))
            map.put("evtJobDate", evtJobDate);
        return getSqlMapClientTemplate().queryForList("BEBMSA.getOldAgeReviewRpt01InjuryPayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 資料 for 紓困貸款抵銷情形照會單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Bebmsa</code> 物件的 List
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDataUpdateRpt03DataBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.selectDataUpdateRpt03ListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Bebmsa</code> 物件的 List
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectOncePayDataForPaymentBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectOncePayDataForPaymentBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失能給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Bebmsa</code> 物件的 List
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectCriPayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectCriPayApplyDataForPaymentBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Bebmsa</code> 物件的 List
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectDiePayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectDiePayApplyDataForPaymentBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Bebmsa</code> 物件的 List
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectInjPayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectInjPayApplyDataForPaymentBy", map);
    }

    /**
     * 依傳入條件取得 申請代算單位 (<code>BEBMSA</code>)下拉選單資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @param bmOldAplDpt 已存在的申請代算單位
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getOldAplDptOptionList(String evtBrDate, String evtIdnNo, String bmOldAplDpt) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);
        map.put("bmOldAplDpt", bmOldAplDpt);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectOldAplDpt", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE,PAYTYP")
    public List<Bebmsa> selectSurvivorReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate, String paytyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(paytyp))
            map.put("paytyp", paytyp);

        return getSqlMapClientTemplate().queryForList("BEBMSA.selectSurvivorReviewRpt01OncePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 老年給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectSurvivorReviewRpt01OldAgePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.selectSurvivorReviewRpt01OldAgePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 失能給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectSurvivorReviewRpt01DisPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.selectSurvivorReviewRpt01DisPayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 職災住院醫療給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectSurvivorReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.selectSurvivorReviewRpt01HosPayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param bmEvidNo 身分證號
     * @param bmEvBrth 出生日期
     * @return
     */
    @DaoFieldList("BMEVIDNO,BMEVBRTH,APNO")
    public List<Bebmsa> selectSurvivorReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectSurvivorReviewRpt01InjuryPayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失蹤給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getSurvivorReviewRpt01DisappearPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01DisPayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>)申請農保死亡給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getSurvivorReviewRpt01FamDiePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getSurvivorReviewRpt01FarmPayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 災保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
	 * @param paytyp 給付種類
     * @return
     */
    @Override
	@DaoFieldList("EVTIDNNO,EVTBRDATE,PAYTYP")
    public List<Bebmsa> getDisableReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate, String paytyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(paytyp))
            map.put("paytyp", paytyp);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01OncePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請農保殘廢給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01FarmPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01FarmPayListBy", map);
    }
    
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失能給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01DisablePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01DisablePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate, String paytype) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(paytype))
            map.put("paytype", paytype);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01DiePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失蹤給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01DisPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01DisPayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>)申請農保死亡給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01FamDiePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01FamDiePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
       
        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01InjuryPayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請老年給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01OldPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
       
        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01OldPayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 職災住院醫療給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectDisableReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.selectDisableReviewRpt01HosPayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔(<code>BEBMSA</code>) 資料 for 給付查詢MasterDetail
     * 
     * @param evtIdnNo 受理編號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Bebmsa</code> 物件的 List
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectDifferenceDetail(String evtIdnNo, String evtBrDate, String sBMPAYKND, String sBMAPNO) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);
        map.put("sBMPAYKND", sBMPAYKND);
        map.put("sBMAPNO", sBMAPNO);        
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectDifferenceDetail", map);
    }

}
