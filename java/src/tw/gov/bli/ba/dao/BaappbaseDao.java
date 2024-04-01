package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;

/**
 * DAO for 給付主檔 (<code>BAAPPBASE</code>)
 * 
 * @author Rickychi
 */
public interface BaappbaseDao {
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @param payKind 給付種類
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDataBy(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqType, String payKind);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @param benIds 受益人社福識別碼
     * @param eqType SQL EqualType
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectDetailDataBy(BigDecimal baappbaseId, String[] procStat, String benIds, String eqType);

    /**
     * 依傳入條件取得 具名領取(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param accRel 戶名與受益人關係
     * @param benEvtRel 受益人與事故者關係
     * @param seqNo 序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectBenListBy(BigDecimal baappbaseId, String apNo, String accRel, String[] benEvtRel, String seqNo);

    /**
     * 依傳入條件取得 失能年金受款人 具名領取(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param accRel 戶名與受益人關係
     * @param benEvtRel 受益人與事故者關係
     * @param seqNo 序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDisabledBenListBy(BigDecimal baappbaseId, String apNo, String accRel, String[] benEvtRel, String seqNo);

    /**
     * 依傳入條件取得 失能年金受款人繼承人的序號 取得 繼承人的序號
     * 
     * @param apNo
     * @param seqNo
     */
    public String getDisabledPayeeHeirSeqNo(String apNo, String seqNo);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertData(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateData(Baappbase baappbase);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 遺屬年金繼承人資料更正作業
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForSurvivorPayeeInheritData(Baappbase baappbase);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 遺屬年金繼承人資料更正作業
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPayeeDataUpdateDetail(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料 for 遺屬年金繼承人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForSurvivorPayeeInheritData(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForSurvivorPayeeDataUpdate(Baappbase baappbase);

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteData(BigDecimal baappbaseId, String procStat);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 行政支援記錄作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectExecutiveSupportQueryBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 行政支援維護作業
     * 
     * @param APNO 受理編號
     * @param ISSUYM 核定年月
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectExecutiveSupportMaintBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 行政支援銷案作業
     * 
     * @param APNO 受理編號
     * @param ISSUYM 核定年月
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectExecutiveSupportMaintForCloseBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) BAAPPBASEID
     * 
     * @param APNO 受理編號
     * @return <code>List</code> 物件
     */
    public List<BigDecimal> selectBaappbaseIdBy(String apno);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) caseTyp欄位資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateCaseTyp(BigDecimal baappbaseId, String caseTyp, String updUser, String updTime);

    /**
     * 取得 SEQUENCE BAAPNO
     */
    public String getSequenceApNo();

    /**
     * 取得 SEQUENCE BAKAPNO
     */
    public String getSequenceKApNo();

    /**
     * 取得 SEQUENCE BASAPNO
     */
    public String getSequenceSApNo();
    
    /**
     * 從 BAS 的 SEQUENCE(BAAPNOK3) 取得受理編號
     * @return
     */
    public String getSequenceApNoK3();

    /**
     * 更新通訊資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForCommunication(Baappbase baappbase);

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayment(Baappbase baappbase);

    /**
     * 更新案件資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForApplication(Baappbase baappbase);

    /**
     * 註銷案件資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbaseId 資料列編號
     * @param caseMk 案件註記
     * @param procStat 處理狀態
     * @param updUser 異動者代號
     * @param updTime 異動日期時間
     * @param upProcStat SQL condition for procStat
     * @param eqType SQL EqualType
     */
    public void cancelForApplication(BigDecimal baappbaseId, String caseMk, String procStat, String updUser, String updTime, String[] upProcStat, String eqType);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 債務繼承人資料登錄作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<Baappbase> selectInheritorRsgisterBy(String apno);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 債務繼承人資料登錄作業
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String selectSeqNoBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 債務繼承人資料
     * 
     * @param APNO 受理編號
     * @param baappbaseId 資料列編號
     * @return <code>BigDecimal</code> 物件
     */
    public Baappbase selectInheritorInfo(String apNo, String baappbaseId);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 債務繼承人資料修改作業
     * 
     * @param apno 受理編號
     * @param benName 繼承人姓名
     * @param benIdnNo 繼承人身分證號
     * @param benBrDate 繼承人出生日期
     * @return <code>Baappbase</code> 物件
     */
    public List<Baappbase> selectInheritorRsgisterForModify(String apno, String benName, String benIdnNo, String benBrDate);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 老年、失能結案狀態變更作業
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<Baappbase> selectPayeeListForOldAgeAndDisabledCloseStatusAlteration(String apNo);
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 遺屬結案狀態變更作業
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<Baappbase> selectPayeeListForCloseStatusAlteration(String apNo, String kind);

    /**
     * 取得 受款人筆數 for 老年、失能結案狀態變更作業
     */
    public String getPayeeDataCountForOldAgeAndDisabledCloseStatusAlteration(String apNo);
    
    /**
     * 取得 受款人筆數 for 遺屬結案狀態變更作業
     */
    public String getPayeeDataCountForCloseStatusAlteration(String apNo);

    /**
     * 更新繼承人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForInheritorRegister(Baappbase baappbase);

    /**
     * 更新處理狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @param updUser 異動者代號
     * @param updTime 異動日期時間
     * @param upProcStat SQL condition for procStat
     * @param eqType SQL EqualType
     */
    public void updateProcStat(BigDecimal baappbaseId, String procStat, String updUser, String updTime, String[] upProcStat, String eqType);

    /**
     * 更新合格註記 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     * @param updUser 異動者代號
     * @param updTime 異動日期時間
     */
    public void updateManchkMk(String apNo, String updUser, String updTime);

    /**
     * 取得 受款人筆數
     */
    public String getPayeeDataCount(String apNo, String benIdnNo, String benBrDate);

    /**
     * 取得 死掉的受款人筆數
     */
    public String getDeadPayeeDataCount(String apNo, String benIdnNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataForInsert(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataForList(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 更正作業 - 死亡改匯 - 繼承人維護
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataListForOldAgeDeathRepayBy(String apNo, String heirSeqNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 失能年金受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<BaappbaseDataUpdateCase> selectDisabledPayeeDataForList(String apno);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledPayeeDataUpdate(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 遺屬年金受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<SurvivorPayeeDataUpdateCase> selectSurvivorPayeeDataForList(String apno, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase getPayeeDataUpdateForUpdateCaseBy(String baappbaseId);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 失能年金受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase getDisabledPayeeDataUpdateForUpdateCaseBy(String baappbaseId);

    /**
     * 依傳入條件取得 事故者的死亡日期是否能修改(<code>BAAPPBASE</code>) 資料 FOR 失能年金受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Boolean isEvtDieDateUpdatableForDisabledPayee(String apNo);

    /**
     * 依傳入條件刪除 繼承人(<code>BAAPPBASE</code>) 資料 FOR 失能年金受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public void deleteDisabledPayeeForCleanEvtDieDate(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 受款人資料更正作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<BaappbaseDataUpdateCase> selectPayeeDataForMustIssueAmt(String apno);

    /**
     * 取得 受款人筆數 For Update
     */
    public String getPayeeDataCountForUpdate(String apNo, String benIdnNo, String benBrDate, String baappbaseId);

    /**
     * 取得 【改匯註銷】按鈕的狀態及使用條件(是否開啟的條件)DataCount1 For Update
     */
    public String getDataCount1ForQuery(String apNo, String seqNo);

    /**
     * 取得 受款人筆數 For DisabledPayeeUpdate
     */
    public String getPayeeDataCountForDisabledUpdate(String apNo, String benIdnNo, String benBrDate, String baappbaseId, String seqNo);

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateForOldAgeDeathRepayDoCancel(Baappbase baappbase);

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeData(Baappbase baappbase);

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForSurvivorPayeeData(Baappbase baappbase);

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeDataAll(Baappbase baappbase);

    /**
     * 取得 受款人筆數
     */
    public String getPayeeDataForSeqNoOne(String apNo);

    /**
     * 取得 受款人筆數
     */
    public String getPayeeDataForSeqNoTwo(String apNo);

    /**
     * 取得 受款人筆數
     */
    public String getPayeeDataForSeqNoThree(String apNo);

    /**
     * 取得 受款人筆數
     */
    public String getPayeeDataForSeqNoFour(String apNo);

    /**
     * 更新受款人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeUpdateData(Baappbase baappbase);

    /**
     * 更新受款人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForOldAgeDeathRepayPayeeUpdateData(Baappbase baappbase);

    /**
     * 更新失能受款人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDisabledPayeeDataUpdate(Baappbase baappbase);

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeDataMustIssueAmt(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 給付審核
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @param payKind 給付種類
     * @param eqType2 SQL EqualType
     * @param pagePayKind
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDataForReviewBy(String apNo, String seqNo, String[] procStat, String eqType, String[] payKind, String eqType2, String pagePayKind);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單 for 給付審核
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>String</code> 物件的 List
     */
    public List<String> selectBenNameBy(String apNo, String benPayMk);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 一次給付資料 for 給付審核
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOncePayDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 審核管制條件
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public Baappbase selectReviewCond(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectBeneficiaryDataBy(String apNo);

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectBenIssueDataBy(String apNo, String seqNo, String[] procStat, String eqType);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 給付審核
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForReview(Baappbase baappbase);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    public List<String> getOldAgeReviewRpt01ApNoListBy(String apNoBegin, String apNoEnd);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectOldAgeReviewRpt01EvtDataBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 受款者資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectOldAgeReviewRpt01BenListBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Baappbase> getOldAgeReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 失能年金紀錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    public List<Baappbase> getOldAgeReviewRpt01DisableAnnuityPayListBy(String apNo, String evtIdnNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬年金紀錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    public List<Baappbase> getOldAgeReviewRpt01SurvivorAnnuityPayListBy(String evtIdnNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 另案扣減資料 - 年金給付 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public String getOldAgeReviewRpt01DeductAnnuityNameBy(String apNo, String seqNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞工紓困貸款繳納本息情形查詢單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectDataUpdateRpt01DataBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 紓困貸款撥款情形查詢清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectDataUpdateRpt02DataBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 紓困貸款抵銷情形照會單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectDataUpdateRpt03DataBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) for 歸檔清單
     * 
     * @param payCode 給付別
     * @param decisionSdate 決行日期起日
     * @param decisionEdate 決行日期迄日
     * @return
     */
    public List<Baappbase> selectDecisionRpt01ListBy(String payCode, String decisionSdate, String decisionEsate, String exeMan);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) for 歸檔清單（遺屬）
     * 
     * @param payCode 給付別
     * @param decisionSdate 決行日期起日
     * @param decisionEdate 決行日期迄日
     * @return
     */
    public List<Baappbase> selectDecisionRpt01ListForSBy(String payCode, String decisionSdate, String decisionEsate, String exeMan);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 調卷/還卷單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectOtherRpt01DataBy(String apNo);

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 給付決行資料 By 受理編號
     * 
     * @param apNo 受理編號
     * @param empId 員工編號
     * @param payCode 給付別
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDecisionDataByApNo(String apNo, String empId, String payCode);

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 給付決行資料 By 列印日期 + 頁次
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @param chkMan 審核人員
     * @param empId 員工編號
     * @param payCode 給付別
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDecisionDataByRptDate(String rptDate, BigDecimal pageNo, String chkMan, String empId, String payCode);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 給付決行
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDecision(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢MasterList
     * 
     * @param apNo 受理編號
     * @param benIdnNo 受款人身分證號
     * @param benName 受款人姓名
     * @param benBrDate 受款人出生日期
     * @param qryCond 查詢條件
     * @param startYm 查詢年月(起)
     * @param endYm 查詢年月(迄)
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectPaymentQueryMasterDataBy(String apNo, String benIdnNo, String benName, String benBrDate, String qryCond, String startYm, String endYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢MasterDetail
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @param payKind 給付別
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectPaymentQueryDetail(String apNo, String payKind, String pagePayKind);

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料 for 給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param qryCond 查詢條件
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectBenIssuDataForPaymentQueryBy(String apNo, String seqNo, String qryCond, String startYm, String endYm);

    /**
     * 更正作業報表 止付單 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public List<Baappbase> selectDataUpdateRpt05ForBaappbase(String apNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>)月處理核定合格清冊 for 列印作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthlyRpt01Report(String apNo, String issuYm);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 紓困貸款呆帳債務人照會單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDataUpdateRpt04DataBy(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectMonthlyRpt05Report(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectMonthlyRpt05Report2(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectMonthlyRpt05Report3(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectMonthlyRpt05ForSurvivorReport(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectMonthlyRpt05PrintDataForSurvivorReport(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataForSurvivorReport(String apNo, String issuYm);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    public List<String> selectMonthlyRpt05ReportCopy1(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    public List<String> selectMonthlyRpt05ReportCopy2(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 給付審核
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectAnnuityPayDataBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 一次給付資料更正-轉公保資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForOnceAmtDataUpdate(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) for 一次給付資料更正-轉公保資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public Baappbase selectDataForOnceAmtDataUpdateByLog(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 核定清單 for 列印作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthlyRpt06Report(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 另案扣減照會單 for 列印作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public Baappbase selectOldAgeReview04Report(String apNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 案件資料更正-事故者姓名資料 for 事故者
     * 
     * @param baappbase 給付主檔
     */
    public void updateEvtDataForApplicationUpdate(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 案件資料更正-事故者姓名資料 for 受款人
     * 
     * @param baappbase 給付主檔
     */
    public void updateBenDataForApplicationUpdate(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 畫面欄位控制
     * 
     * @param apNo 受理編號
     */
    public Integer selectForPayeeDataUpdateQ2(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param apNo 受理編號
     */
    public Integer selectForPayeeDataUpdateRelationQ1(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param apNo 受理編號
     */
    public Integer selectForPayeeDataUpdateRelationQ2(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param apNo 受理編號
     */
    public Integer selectForPayeeDataUpdateRelationQ3(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param brDate 事故者出生日期
     * @param idnNo 事故者身份字號
     */
    public Integer selectForPayeeDataUpdateRelationQ4(String brDate, String idnNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 刪除按鈕控制
     * 
     * @param apNo 受理編號
     */
    public Integer selectForPayeeDataUpdateQueryQ4(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者存活狀態
     * 
     * @param apNo 受理編號
     * @return <code>Integer</code> 物件
     */
    public Integer selectEvtAliveStatusBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 被具名領取筆數
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Integer</code> 物件
     */
    public Integer selectAccSeqNoAmt(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 帳號資料
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectBankDataBy(BigDecimal baappbaseId, String accSeqNo);

    /**
     * 依傳入條件刪除 給付主檔(<code>BAAPPBASE</code>) 債務繼承人資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappbase</code> 物件
     */
    public void deleteForInheritorRegister(String apNo, String seqNo);

    /**
     * 刪除 給付主檔 (<code>BAAPPBASE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteBaappbaseData(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public List<Baappbase> selectBaappbaseDataByLog(String apNo, String seqNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 受款人資料更正 刪除受款人資料後更新
     * 
     * @param baappbase 給付主檔
     */
    public void updateProcstatForPayeeDataUpdate(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 案件資料更正"註銷"按鈕狀態
     * 
     * @param apNo 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal checkDelBtnForPaymentUpdate(String apNo);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledAnnuityReceipt(Baappbase baappbase);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金受理作業 國保資料 國保資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataDisabledAnnuityReceiptFor36(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金給付資料 事故者資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDisabledAnnuityReceipt(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金給付資料 眷屬資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDisabledDetail(Baappbase baappbase);

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteDataForDisabledAnnuityReceipt(BigDecimal baappbaseId, String procStat);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 失能年金給付受理眷屬清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectBenDataListBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @param benIds 受益人社福識別碼
     * @param eqType SQL EqualType
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectDetailDataForDisabledAnnuityReceiptBy(BigDecimal baappbaseId, String seqNo, String[] procStat, String eqType);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectDetailDataForSurvivorAnnuityReceiptBy(BigDecimal baappbaseId, String seqNo, String[] procStat, String eqType);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料清單 for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public List<Baappbase> selectDetailDataListForSurvivorAnnuityReceiptBy(String apNo, String seqNo, String[] procStat, String eqType);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 失能年金受理作業
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @param payKind 給付種類
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDataForAnnuityReceiptBy(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqType, String payKind);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 遺屬年金受理作業
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectSurvivorEvtDataListBy(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqType);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬資料清單 for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectSurvivorBenDataListBy(String apNo, String[] procStat, String eqType);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 判斷確認按鈕狀態條件 for 給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal checkBtnStatusForPaymentReview(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 序號 for 給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public String getNewSeqNoForSurvivorAnnuity(String apNo);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 事故者資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertEvtDataForSurvivorAnnuityReceipt(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 事故者資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 事故者資料
     * 
     * @param baappbaseList 給付主檔
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(final List<Object> baappbaseList);

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 遺屬資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBenDataForSurvivorAnnuityReceipt(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 遺屬資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateBenDataForSurvivorAnnuityReceipt(Baappbase baappbase);

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 受理資料 By BAAPPBASEID for 遺屬年金受理作業
     * 
     * @param baappbase 給付主檔
     */
    public void deleteDataForSurvivorAnnuityReceiptById(BigDecimal baappbaseId, String procStat);

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 所有受理資料 By APNO for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     */
    public void deleteAllDataForSurvivorAnnuityReceiptByApNo(String apNo);

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 「事故者姓名」欄位是否可以修改 for 案件資料更正
     * 
     * @param apNo 受理編號
     * @return <code>BigDecimal</code>
     */
    public BigDecimal selectEvtNameModifyStatusForApplicationUpdate(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保老年年金給 另案扣減照會單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectOldAgeReviewRpt04DataBy(String apNo, String seqNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 具名領取 資料列編號
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public List<BigDecimal> selectBaappbaseIdByAccSeqNo(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 帳號資料
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectBankDataByAccSeqNo(String apNo, String accSeqNo);

    /**
     * 依傳入條件取得 處理函別(<code>BAAPPBASE</code>) 更新條件
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Integer getProCstatUpdateLetterType(String apNo, String issuYm);

    /**
     * 依傳入條件 (<code>BAAPPBASE</code>) 取得 按鈕狀態的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer getButtonStatus(String apNo);

    /**
     * 依傳入的條件取得 是否為已註銷 (<code>BAAPPBASE</code>) 的案件資料<br>
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer getProcastatStatus(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 處理狀態
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>String</code> 物件
     */
    public String selectProcStatBy(String apNo, String seqNo);

    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public void updateForCheckMarkLevelAdjust(String apNo);

    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public void updateCloseDateForCheckMarkLevelAdjust(String apNo);

    /**
     * 依傳入條件取得(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public Baappbase selectForCheckMarkLevelAdjustByLog(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 for 給付審核
     * 
     * @param apNo 受理編號
     */
    public Integer selectDataCountForReviewUpdate(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金編審註記程度調整 (Header 資料)
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getDisabledCheckMarkLevelAdjustHeaderDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金編審註記程度調整 (受款人資料)
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> getDisabledCheckMarkLevelAdjustBenListBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金編審註記程度調整 (Header 資料)
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getSurvivorCheckMarkLevelAdjustHeaderDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金編審註記程度調整 (受款人資料)
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> getSurvivorCheckMarkLevelAdjustBenListBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getDisabledApplicationDataUpdateMasterDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 給付查詢重新查核失能程度
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getDisabledApplicationDataUpdateMasterDataForRehcBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (是否顯示 註銷 按鍵)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則顯示
     */
    public Integer getDisabledApplicationDataUpdateIsShowCancelButtonBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (事故者姓名 可否修改)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則可修改
     */
    public Integer getDisabledApplicationDataUpdateCanModifyEvtNameBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @return <code>BAAPPBASEID</code> 資料列編號
     */
    public List<BigDecimal> getDisabledApplicationDataUpdateListForUpdateBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (依指定的資料列編號取得須更新的給付主檔資料)
     * 
     * @param baappbaseId 資料列編號
     * @return
     */
    public Baappbase getDisabledApplicationDataUpdateMasterDataByBaappbaseId(BigDecimal baappbaseId);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正
     * 
     * @param dataList
     */
    public void updateDisabledApplicationDataUpdateData(final List<Baappbase> dataList);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (身分證重號時)
     * 
     * @param dataList
     */
    public void updateDisabledApplicationDataUpdateDupeIdnData(final List<Baappbase> dataList);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (須註銷的資料列編號)
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<BigDecimal> getDisabledApplicationDataUpdateListForDeleteBy(String apNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (註銷案件)
     * 
     * @param apNo 受理編號
     * @param userData 使用者物件
     */
    public void deleteDisabledApplicationDataUpdateData(String apNo, UserBean userData);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getSurvivorApplicationDataUpdateMasterDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (是否顯示 註銷 按鍵)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則顯示
     */
    public Integer getSurvivorApplicationDataUpdateIsShowCancelButtonBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (事故者姓名 可否修改)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則可修改
     */
    public Integer getSurvivorApplicationDataUpdateCanModifyEvtNameBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @return <code>BAAPPBASEID</code> 資料列編號
     */
    public List<BigDecimal> getSurvivorApplicationDataUpdateListForUpdateBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (依指定的資料列編號取得須更新的給付主檔資料)
     * 
     * @param baappbaseId 資料列編號
     * @return
     */
    public Baappbase getSurvivorApplicationDataUpdateMasterDataByBaappbaseId(BigDecimal baappbaseId);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正
     * 
     * @param dataList
     */
    public void updateSurvivorApplicationDataUpdateData(final List<Baappbase> dataList);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (身分證重號時)
     * 
     * @param dataList
     */
    public void updateSurvivorApplicationDataUpdateDupeIdnData(final List<Baappbase> dataList);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (須註銷的資料列編號)
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<BigDecimal> getSurvivorApplicationDataUpdateListForDeleteBy(String apNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (註銷案件)
     * 
     * @param apNo 受理編號
     * @param userData 使用者物件
     */
    public void deleteSurvivorApplicationDataUpdateData(String apNo, UserBean userData);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用受理作業 (Header for Add)
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getReviewFeeReceiptHeaderDataForInsertBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用受理作業 (Header for Update)
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    public Baappbase getReviewFeeReceiptHeaderDataForUpdateBy(String apNo, String evtIdnNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用審核作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getReviewFeeReviewHeaderDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用決行作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase getReviewFeeDecisionHeaderDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的決行層級資料 for 複檢費用決行作業
     * 
     * @param apNo 受理編號
     * @param empNo 使用者員工編號
     * @return
     */
    public Baappbase getReviewFeeDecisionExcLvlBy(String apNo, String empNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    public List<String> getDisableReviewRpt01ApNoListBy(String apNoBegin, String apNoEnd);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectDisableReviewRpt01EvtDataBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 受款者資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectDisableReviewRpt01BenListBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Baappbase> getDisableReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 申請國保身障年金給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public List<Baappbase> getDisableReviewRpt01NbPayListBy(String evtIds);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 老年年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Baappbase> getDisableReviewRpt01OldPayListBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Baappbase> getDisableReviewRpt01SurvivorPayListBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    public List<String> selectSurvivorReviewRpt01ApNoListBy(String apNoBegin, String apNoEnd);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectSurvivorReviewRpt01EvtDataBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 受款者資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectSurvivorReviewRpt01BenListBy(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 繼承人資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectSurvivorReviewRpt01BenListByExt(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectSurvivorRpt01BenDataList(String apNo, String seqNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 繼承人資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectSurvivorRpt01ExtDataList(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 繼承人資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectSurvivorRpt01ExtRefDataList(String apNo, String seqNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Baappbase> selectSurvivorReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Baappbase> selectSurvivorReviewRpt01OldPayListBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Baappbase> selectSurvivorReviewRpt01DisablePayListBy(String apNo, String evtIdnNo, String evtBrDate);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 已存在資料ID for 遺屬屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param benIdnNo 受款人身分證號
     * @param benBrDate 受款人出生日期
     * @param baappbaseId 資料列編號
     * @param eqType SQL EqualType
     * @return 內含<code>BigDecimal</code> 物件的List
     */
    public List<BigDecimal> selectExistDataIdForSurvivorAnnuityReceipt(String apNo, String benIdnNo, String benBrDate, BigDecimal baappbaseId, String eqType);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 被共同具領之受款人員序號 for 遺屬屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param benIdnNo 受款人身分證號
     * @return <code>String</code> 物件
     */
    public String selectAccSeqNoForSurvivorAnnuityReceipt(String apNo, String benIdnNo);

    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) 具領人序號 for 遺屬屬年金受理作業
     * 
     * @param accSeqNo 具領人序號
     * @param baappbaseId 資料列編號
     */
    public void updateAccSeqNoForSurvivorAnnuityReceipt(String accSeqNo, BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付主檔繼承自誰的(<code>BAAPPBASE</code>) 資料 FOR 失能年金受款人資料修改作業
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase getAncestorInfoBySeqNo(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔繼承自誰的(<code>BAAPPBASE</code>) 事故者/受款人資料 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public List<Baappbase> selectEvtBenDataForSurvivorPaymentReview(String apNo);

    /**
     * 依傳入條件取得 給付主檔繼承自誰的(<code>BAAPPBASE</code>) 事故者/受款人核定資料 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param eqType SQL EqualType
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public List<Baappbase> selectEvtBenIssuDataForSurvivorPaymentReview(String apNo, String seqNo, String eqType);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人下拉選單 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public List<Baappbase> selectSeqNoListForSurvivorPaymentReview(String apNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) FOR 遺屬年金給付審核
     * 
     * @param baappbase 給付主擋
     */
    public void updateDataForSurvivorPaymentReview(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 (判斷『給付主檔』目前的處理狀態是否可以更新) FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public Integer selectDataCount1ForSurvivorPaymentReview(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 (判斷目前各給付年月是否已審核完成) FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public Integer selectDataCount2ForSurvivorPaymentReview(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public List<Baappbase> selectAccDataForSurvivorAnnuityReceipt(String apNo, String seqNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param baappbase 給付主檔
     */
    public void updateAccDataForSurvivorAnnuityReceipt(Baappbase baappbase);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param dataList
     */
    public void updateAccDataForSurvivorAnnuityReceipt(final List<Baappbase> dataList);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 事故者資料 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */

    public List<Baappbase> selectDependantEvtDataForList(String apNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param dataList
     */
    // 若此SQL條件有所更改，請一併修改 UpdateService.isCleanCoreceivePaymentData()
    public void cleanCoreceivePaymentData(String apNo, String seqNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 立帳對象姓名 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>String</code> 物件
     */
    public String selectDisablePayDeductBenname(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectDependantDataList(String apNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDependant(Baappbase baappbase);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 (判斷案件資料更正是否需輸入'發放方式')
     * 
     * @param APNO 受理編號
     * @return <code>Integer</code> 物件
     */
    public Integer chkInterValMonthStatus(String apNo, String equalTyp);

    /**
     * 取得 INTERVALMONTH
     */
    public String getInterValMonth(String apNo, String seqNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) FOR 列印歸檔清單
     * 
     * @param dataList
     */
    public void updateDataForDecisionRpt01(final List<Baappbase> dataList);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateRegetCipbMkByApNo(String apNo, String regetCipbMk);

    // OldAge Death Repay begin

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 (檢核受款人資料是否符合可改匯處理的資格)
     * 
     * @param baappbase 給付主檔
     */
    public List<Baappbase> selectCheckOldAgeDeathRepayDataBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 退匯資料檔(<code>BAAPPBASE</code>) 資料 for 符合資料，有退匯資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @param heirSeqNo 繼承人序
     * @return
     */
    public List<Baappbase> selectOldAgeDeathRepayDataBy(String apNo, String seqNo, String heirSeqNo);

    /**
     * 依傳入條件取得 退匯資料檔(<code>BAAPPBASE</code>) 資料 for 繼承人資料
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序
     * @return
     */
    public List<Baappbase> selectHeirSeqNoExistDataBy(String apNo, String heirSeqNo);

    /**
     * 依傳入條件取得 退匯資料檔(<code>BAAPPBASE</code>) 資料 for 繼承人資料
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序
     * @return
     */
    public List<Baappbase> selectRepayIssueAmtDataBy(String apNo, String heirSeqNo);

    /**
     * 每月取得分配人數 依傳入條件取得 退匯資料檔(<code>BAAPPBASE</code>) 資料 for 繼承人金額分配資料
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序
     * @return
     */
    public String selectRepayIssueAmtDataCountBy(String apNo, String heirSeqNo);
    // OldAge Death Repay end

    // OldAge Payment Receive begin
    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectKCashReceiveDataBy(String apNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectSCashReceiveDataBy(String apNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<Baappbase> selectCashReceiveDataBy(String apNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回註銷
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectChkCancelCashReceiveDataBy(String apNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectRemittanceReceiveDataBy(String apNo, String seqNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectDisabledRemittanceReceiveDataBy(String apNo, String seqNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectSurvivorRemittanceReceiveDataBy(String apNo, String seqNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectChkCancelRemittanceReceiveDataBy(String apNo, String seqNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectChkCancelDisabledRemittanceReceiveDataBy(String apNo, String seqNo, String payCode);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectChkCancelSurvivorRemittanceReceiveDataBy(String apNo, String seqNo, String payCode);
    // OldAge Payment Receive end

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 死亡改匯繼承人維護作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public Baappbase selectDataForPayeeDataUpdateDetail(String apNo);

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金平均薪資
     * 
     * @param apNo 受理編號
     * @return caseObj
     */
    public Baappbase selectDataForSurvivorAvgAmtDetail(String apNo);

    /**
     * 取得 目前失能最大APNO
     */
    public String getMaxApno();

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getReceiveNameBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public List<Baappbase> getReceiveNameCase2By(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getSingleReceiveNameBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getSingleReceiveNameCase2By(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔 資料清單 FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @return 內含 <code>String</code> 物件的 List
     */
    public List<Baappbase> getReceiveBenNameBy(String apNo, String grdName);

    /**
     * 依傳入條件取得 給付主檔 資料清單 FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @return 內含 <code>String</code> 物件的 List
     */
    public List<Baappbase> getReceiveBenNameCase2By(String apNo, String grdName);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getReceivePayTypBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29DisabledMDataBy();

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29SurvivorMDataBy();

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29DisabledYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 重新查核失能程度通知函
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30K01DataBy(String payCode, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 通過查核續發失能年金通知函
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30K02DataBy(String payCode, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 重新查核失能程度通知函 (EXCEL)
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30ExeclK01DataBy(String payCode, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 通過查核續發失能年金通知函 (EXCEL)
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30ExeclK02DataBy(String payCode, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 批次老年差額金通知 - 第一次通知函 001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001DataBy(String payCode, String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第一次通知函 001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001FDataBy(String payCode, String apNo);
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 批次老年差額金通知 - 第一次通知函 002
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002DataBy(String payCode, String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第一次通知函 002 和 第一次通知函003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002And31003FDataBy(String payCode, String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第一次通知函 003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31003DataBy(String payCode, String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第二次通知函（催辦）001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001UDataBy(String payCode, String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第二次通知函（催辦）002
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002UDataBy(String payCode, String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第二次通知函（催辦）003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31003UDataBy(String payCode, String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第三次通知函（延不補正）001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001PDataBy(String payCode, String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第三次通知函（延不補正）002
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002PDataBy(String payCode, String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第三次通知函（延不補正）003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31003PDataBy(String payCode, String apNo, String issuYm);

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（1-第1次發函）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify1(String payCode, String issuYm);

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（2-催辦）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify2(String payCode, String issuYm);

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（3-延不補正）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify3(String payCode, String issuYm);

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（全部）
     */
    public List<Baappbase> selectApNoListForOtherRpt12NotifyA(String payCode, String issuYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29SurvivorYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能 06
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29DisabledY06B01DataBy(String studeChkMonth, String studeDate, String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬 06
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29SurvivorY06B01DataBy(String studeChkMonth, String studeDate, String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclDisabledMDataBy();

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclSurvivorMDataBy();

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclDisabledYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclSurvivorYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能 06
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclDisabledY06DataBy(String studeChkMonth, String studeDate, String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬 06
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclSurvivorY06DataBy(String studeChkMonth, String studeDate, String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 逾期未決行案件管制表
     * 
     * @param payCode 給付別
     * @param endYm 截止年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectAuditRpt01DataBy(String payCode, String endYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 逾期未決行案件管制表 Excel
     * 
     * @param payCode 給付別
     * @param endYm 截止年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectAuditRpt01ExcelDataBy(String payCode, String endYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 老年
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1LDataBy(String crtDate, String crtYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 失能
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1KDataBy(String crtDate, String crtYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 遺屬
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1SDataBy(String crtDate, String crtYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 全給付別
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1DataBy(String crtDate, String crtYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 老年
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2LDataBy(String crtDate, String crtYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 失能
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2KDataBy(String crtDate, String crtYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 遺屬
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2SDataBy(String crtDate, String crtYm);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 全給付別
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2DataBy(String crtDate, String crtYm);

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次
     */
    public List<String> selectForPrintNotify(String payCode, String issuYm, String rptKind);

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次 遺屬
     */
    public List<String> selectSurvivorForPrintNotify(String payCode, String issuYm);

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次 失能
     */
    public List<String> selectDisabledForPrintNotify(String payCode, String issuYm);

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 資料清單 老年
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthCheckLDataListBy(String payCode, String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI);

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 資料清單 失能
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthCheckKDataListBy(String payCode, String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI);

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 資料清單 遺屬
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthCheckSDataListBy(String payCode, String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI);

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 查詢每個受理案件及處理狀態
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectChkMonthDataBy(String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢 保密資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectIdnNoDataBy(String apNo);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 最大給付年月資料 for 眷屬資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public String selectMaxPayYmBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 最大給付年月資料 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public String selectMaxPayYmForSBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 受款人數(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getPayeeCount(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受理案件的第一個受款人之基本資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectReportReplaceDataForA077(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受理案件的第一個受款人之基本資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baappbase> selectReportReplaceDataForA089(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料是否存在
     * 
     * @param idn 身分證號
     * @return
     */
    public int selectEvtPersonDataCount(String idn);

    /**
     * 依傳入條件取得受理編號
     * 
     * @param idn 身分證號
     * @return
     */
    public List<String> selectApnoDataFromMultiSource(String idn);

    /**
     * 依傳入條件取得 地址資料
     * 
     * @param apno 受理編號
     * @return
     */
    public Baappbase selectAddrData(String apno, String seqNo);

    /**
     * 依傳入條件取得 受理編號的事故者姓名
     * 
     * @param IDN 身分證號
     * @return
     */
    public String selectEvtName(String idn);
    
    /**
     * 依傳入條件取得 受理編號的事故者姓名
     * 
     * @param idn 身分證號 apno 受理編號
     * @return
     */
    public String selectEvtNameByIdnAndApno(String idn, String apno);

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public List<Baappbase> selectBatchPaymentReceiveDataBy(String apNo, String payCode, String chkDate);

    /**
     * 更正作業報表 止付單 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public List<Baappbase> selectSeqNoListForDataUpdateRpt05(String apNo, String seqNo);

    /**
     * 依傳入條件取得 繳款單受益者資料 (<code>BAAPPBASE</code>)
     * 
     * @param PAYMENTNO 繳款單號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase getBaappbaseData(String paymentNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateIssuNotifyingMkByApNo(String apNo);

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateUnqualifiedCauseByApNo(String apNo, String seqNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectMonthlyRpt05PrintData(String apNo);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> selectMonthlyRpt05PrintCase2Data(String apNo, String issuYm);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金 for 通知書欄位代碼 A150 不合格說明
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataA150(String apNo, String issuYm);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> selectMonthlyRpt05PrintCase2Data1(String apNo, String issuYm);
    
    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataForSurvivorReport1(String apNo, String issuYm);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataForSurvivorReport2(String apNo, String issuYm);

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectMonthlyRpt05PrintDataForSurvivorReport1(String apNo);

    /**
     * 依傳入條件取得 主檔受益人資料
     * 
     * @param apno 受理編號
     * @param idnNo 身分證號
     * @return
     */
    public Baappbase selectPaymentBaseData(String apno,String idnNo);
    
    /**
     * 查詢受益人IDN對應姓名
     * 
     * @param idn
     * @return benName
     */
    public String selectPaymentBenName(String idn);
    
    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) FOR 遺屬受理審核清單核定通知書受文者身份證號
     * 
     * @param apNo 受理編號
     * @param name 受文者姓名
     * @return <code>BigDecimal</code> 物件
     */
    public String getSingleReceiveIdnNoBy(String apNo, String name);

    /**
     * 更新老年、失能結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param seqNo
     */
    public void updateDataForOldAgeAndDsiabledCloseStatusAlteration(String apNo, String updUser);
    
    /**
     * 更新遺屬結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param seqNo
     */
    public void updateDataForCloseStatusAlteration(String apNo, String seqNo, String updUser);
    
    /**
     * 更新結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param updUser
     */
    public void updateCaseTypForCloseStatusAlteration(String apNo, String updUser);
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 重設案件給付參數資料
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase selectDataForResetPaymentParameter(String apNo);

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 重設案件給付參數資料
     * 
     * @param apNo 受理編號
     */
    public List<Baappbase> selectDataToReGetCipbForResetPaymentParameter(String apNo);
    
    /**
     * 依傳入條件取得 (<code>NBSCHOOL</code>) 資料 FOR 
     * 
     * @param key 身分證號
     */
    public String selectMaxDateFromNbschool(String key);
    
    /**
     * 依傳入條件取得 (<code>NBSCHOOL</code>) 資料 FOR 
     * 
     * @param key 身分證號
     * @param statusDate_S 狀態日期（起）
     */
    public Baappbase selectMaxDateFromNbschool2(String key, String statusDate_S);
    
    /**
     * 依傳入條件取得 資料 FOR 年金受理資料轉出
     * 
     * @param apNo 受理編號
     */
    public List<String> ForAnnuityAcceptDataTransfer(String apNo);

    /**
     * 依傳入條件取得 資料 FOR 老年、失能年金補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> qryAddManPayList(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 資料 FOR 補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> qryUpdManPayList(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 資料 FOR 遺屬年金補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> qryAddManPayListForSurvivor(String apNo, String issuYm);
    
    /**
     * 依傳入條件更新 CASEMK(案件註記)欄位
     * @param apno
     * @param seqno
     */
    public int updateCasemkByApnoAndSeqno(String apno, String seqno, String updUser);
}
