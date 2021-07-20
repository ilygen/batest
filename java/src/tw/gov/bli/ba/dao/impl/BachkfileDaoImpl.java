package tw.gov.bli.ba.dao.impl;

/**
 * DAO for 給付編審檔 (<code>BACHKFILE</code>)
 * 
 * @author Rickychi
 */
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BachkfileDao;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.update.cases.ChkFileCase;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

@DaoTable("BACHKFILE")
public class BachkfileDaoImpl extends SqlMapClientDaoSupport implements BachkfileDao {

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 案件資料更正
     * 
     * @param baappbaseId 資料列編號
     * @return <code>ChkFileCase</code> 物件
     */
    @DaoFieldList("BAAPPBASEID")
    public List<ChkFileCase> selectChkListBy(BigDecimal baappbaseId) {
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectChkListBy", baappbaseId);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     * @param orderBy 排序方式
     * @return <code>ChkFileCase</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectChkListForCheckMarkLevelAdjust(String apNo, String orderBy) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(apNo)) {
            map.put("orderBy", orderBy);
        }
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectChkListForCheckMarkLevelAdjust", map);
    }

    /**
     * 更新 編審註記參數檔(<code>BACHKFILE</code>) 資料
     * 
     * @param baChkFileId
     * @param chkCodePost
     */
    public void updateChkCodePost(String baChkFileId, String chkCodePost) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(baChkFileId)) {
            map.put("baChkFileId", baChkFileId);
        }
        if (StringUtils.isNotBlank(chkCodePost)) {
            map.put("chkCodePost", chkCodePost);
        }
        getSqlMapClientTemplate().update("BACHKFILE.updateChkCodePost", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料
     * 
     * @param baChkFileId
     * @param chkCodePost
     */
    public Bachkfile selectChkCodePostByLog(String baChkFileId) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(baChkFileId)) {
            map.put("baChkFileId", baChkFileId);
        }
        return (Bachkfile) getSqlMapClientTemplate().queryForObject("BACHKFILE.selectChkCodePostByLog", map);
    }

    /**
     * 計算 編審註記參數檔(<code>BACHKFILE</code>) CHKCODEPOST 資料
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public BigDecimal countForCheckMarkLevelAdjust(String apNo) {
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BACHKFILE.countForCheckMarkLevelAdjust", apNo);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewEvtChkListForOldAge(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewEvtChkListForOldAge", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料 FOR 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewBenChkListForOldAge(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewBenChkListForOldAge", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 FOR 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewEvtChkListForDisabled(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewEvtChkListForDisabled", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 眷屬編審註記資料 FOR 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewBenChkListForDisabled(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewBenChkListForDisabled", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 眷屬符合註記資料 FOR 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewOtherChkListForDisabled(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewOtherChkListForDisabled", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewEvtChkListForSurvivor(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewEvtChkListForSurvivor", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 遺屬編審註記資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewBenChkListForSurvivor(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewBenChkListForSurvivor", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 遺屬符合註記資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO")
    public List<Bachkfile> selectReviewOtherChkListForSurvivor(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewOtherChkListForSurvivor", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param chkTyp 編審註記種類
     * @param familySeqNo 眷屬資料序號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO,SEQNO,CHKTYP")
    public List<Bachkfile> selectReviewChkListBy(String apNo, String seqNo, String chkTyp, String familySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("chkTyp", chkTyp);
        if (StringUtils.isNotBlank(familySeqNo)) {
            map.put("familySeqNo", familySeqNo);
        }
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectReviewChkListBy", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bachkfile> selectBenChkDataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectBenChkDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bachkfile> getOldAgeReviewRpt01ChkfileDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getOldAgeReviewRpt01ChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bachkfile> getOldAgeReviewRpt01ChkfileDescBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getOldAgeReviewRpt01ChkfileDescBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getOldAgeReviewRpt01BenChkfileDataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getOldAgeReviewRpt01BenChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料 FOR 給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param qryCond 查詢條件
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO,SEQNO,QRYCOND,STARTYM,ENDYM")
    public List<Bachkfile> selectBenChkDataForPaymentQueryBy(String apNo, String seqNo, String qryCond, String startYm, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("qryCond", qryCond);
        map.put("startYm", startYm);
        map.put("endYm", endYm);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectBenChkDataForPaymentQueryBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public String selectForRptReplace(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BACHKFILE.selectForRptReplace", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public String selectForRptReplaceA137(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BACHKFILE.selectForRptReplaceA137", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer selectForPayeeDataUpdateQ1(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Integer) getSqlMapClientTemplate().queryForObject("BACHKFILE.selectForPayeeDataUpdateQ1", map);
    }

    /**
     * 依傳入條件取得 給付編審檔 For 失能年金受款人資料更正作業是否顯示身分查核年月 檢查傳入的受款人是否有W1,WH,WJ註記
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public boolean displayIdnChkYearMonth(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("BACHKFILE.displayIdnChkYearMonth", map);
        return count > 0;
    }

    /**
     * 依傳入條件取得 給付編審檔 For 遺屬年金受款人資料更正作業是否顯示再婚日期 檢查傳入的受款人是否有5G註記
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public boolean displayDigamyDate(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("BACHKFILE.displayDigamyDate", map);
        return count > 0;
    }

    /**
     * 刪除 給付編審檔 (<code>BACHKFILE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteBachkfileData(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        getSqlMapClientTemplate().delete("BACHKFILE.deleteBachkfileData", map);
    }

    /**
     * 依傳入條件取得 給付編審檔 (<code>BACHKFILE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public List<Bachkfile> selectBachkfileDataByLog(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectBachkfileDataByLog", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getDisableReviewRpt01BenChkfileDataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisableReviewRpt01BenChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bachkfile> getDisableReviewRpt01ChkfileDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisableReviewRpt01ChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bachkfile> getDisableReviewRpt01ChkfileDescBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisableReviewRpt01ChkfileDescBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> selectSurvivorReviewRpt01ChkfileDataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectSurvivorReviewRpt01ChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 失能年金編審註記程度調整 (受款人編審註記資料)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bachkfile> getDisabledCheckMarkLevelAdjustBenDetailDataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisabledCheckMarkLevelAdjustBenDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 失能年金編審註記程度調整 (眷屬編審註記資料)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bachkfile> getDisabledCheckMarkLevelAdjustFamDetailDataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisabledCheckMarkLevelAdjustFamDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 遺屬年金編審註記程度調整 (受款人編審註記資料)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bachkfile> getSurvivorCheckMarkLevelAdjustBenDetailDataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorCheckMarkLevelAdjustBenDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 失能年金案件資料更正 (編審註記資料)
     * 
     * @param baappbaseId 主檔資料列編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public List<Bachkfile> getDisabledApplicationDataUpdateCheckFileListBy(BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("baappbaseId", baappbaseId);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisabledApplicationDataUpdateCheckFileListBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bachkfile> getSurvivorReviewRpt01ChkfileDescBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorReviewRpt01ChkfileDescBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 遺屬年金案件資料更正 (編審註記資料)
     * 
     * @param baappbaseId 主檔資料列編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public List<Bachkfile> getSurvivorApplicationDataUpdateCheckFileListBy(BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("baappbaseId", baappbaseId);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorApplicationDataUpdateCheckFileListBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        map.put("eqType", StringUtility.changOperator("<>"));

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisableReviewRpt01FamChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDescBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisableReviewRpt01FamChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDescDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        map.put("eqType", StringUtility.changOperator("<>"));

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisableReviewRpt01FamChkfileDescDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDescData(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getDisableReviewRpt01FamChkfileDescDataBy", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param chkTyp 編審註記種類
     * @param familySeqNo 眷屬資料序號
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO,CHKTYP")
    public List<Bachkfile> selectChkFileForSurvivorPaymentReview(String apNo, String chkTyp, String familySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("chkTyp", chkTyp);
        map.put("familySeqNo", familySeqNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectChkFileForSurvivorPaymentReview", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 遺屬年金受款人資料更正
     * 
     * @param apNo 受理編號
     * @param chkTyp 編審註記種類
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bachkfile> getSurvivorPayeeQualifyCheckMark(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorPayeeQualifyCheckMark", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 遺屬年金受款人資料更正
     * 
     * @param apNo 受理編號
     * @param chkTyp 編審註記種類
     * @return <code>Bachkfile</code> 物件 List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bachkfile> getSurvivorPayeeCheckMark(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorPayeeCheckMark", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getSurvivorReviewRpt01ChkfileDataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        map.put("eqType", StringUtility.changOperator("<>"));

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorReviewRpt01BenChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getSurvivorReviewRpt01BenChkfileBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorReviewRpt01BenChkfileDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getSurvivorReviewRpt01ChkfileDescDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        map.put("eqType", StringUtility.changOperator("<>"));

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorReviewRpt01BenChkfileDescDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bachkfile> getSurvivorReviewRpt01BenChkfileDescData(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BACHKFILE.getSurvivorReviewRpt01BenChkfileDescDataBy", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 眷屬資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public Integer selectDataCount2ForDependant(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        return (Integer) getSqlMapClientTemplate().queryForObject("BACHKFILE.selectDataCount2ForDependant", map);
    }

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 補送在學證明案件明細資料
     * 
     * @param apNo 受理編號
     * @param seqNo 排序方式
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return <code>BACHKFILE</code> 物件
     */
    public List<Bachkfile> selectChkCodeForRpt29By(String apNo, String seqNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        if (StringUtils.isNotBlank(payYm)) {
            map.put("payYm", payYm);
        }
        return getSqlMapClientTemplate().queryForList("BACHKFILE.selectChkCodeForRpt29By", map);
    }

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public String selectInsuranceSalaryForRptReplace(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BACHKFILE.selectInsuranceSalaryForRptReplace", map);
    }

}
