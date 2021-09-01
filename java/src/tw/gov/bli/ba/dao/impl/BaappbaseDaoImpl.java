package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import tw.gov.bli.ba.dao.BaappbaseDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 給付主檔 (<code>BAAPPBASE</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAAPPBASE")
public class BaappbaseDaoImpl extends SqlMapClientDaoSupport implements BaappbaseDao {
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @param payKind 給付種類
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("EVIDNNO,APNO,SEQNO,PROCSTAT,EQTYP,PAYKIND")
    public List<Baappbase> selectDataBy(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqType, String payKind) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(evtIdnNo)) {
            map.put("evtIdnNo", evtIdnNo);
        }
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        if (StringUtils.isNotBlank(payKind)) {
            map.put("payKind", payKind);
        }
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @param benIds 受益人社福識別碼
     * @param eqType SQL EqualType
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,PROCSTAT,BENIDS,EQTYPE")
    public Baappbase selectDetailDataBy(BigDecimal baappbaseId, String[] procStat, String benIds, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(benIds)) {
            map.put("benIds", benIds);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDetailDataBy", map);
    }

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
    @DaoFieldList("BAAPPBASEID,APNO,ACCREL,BENEVTREL,SEQNO")
    public List<Baappbase> selectBenListBy(BigDecimal baappbaseId, String apNo, String accRel, String[] benEvtRel, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        if (baappbaseId != null)
            map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(accRel))
            map.put("accRel", accRel);
        if (benEvtRel != null && benEvtRel.length > 0)
            map.put("benEvtRel", benEvtRel);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBenListBy", map);
    }

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
    @DaoFieldList("BAAPPBASEID,APNO,ACCREL,BENEVTREL,SEQNO")
    public List<Baappbase> selectDisabledBenListBy(BigDecimal baappbaseId, String apNo, String accRel, String[] benEvtRel, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        if (baappbaseId != null)
            map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(accRel))
            map.put("accRel", accRel);
        if (benEvtRel != null && benEvtRel.length > 0)
            map.put("benEvtRel", benEvtRel);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDisabledBenListBy", map);
    }

    /**
     * 依傳入條件取得 失能年金受款人繼承人的序號 取得 繼承人的序號
     * 
     * @param apNo
     * @param seqNo
     */
    @DaoFieldList("APNO,SEQNO")
    public String getDisabledPayeeHeirSeqNo(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getDisabledPayeeHeirSeqNo", map);
    }

    /**
     * 取得 目前失能最大APNO
     */
    public String getMaxApno() {

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getMaxApno");
    }

    /**
     * 依傳入條件取得 失能年金受款人事故者死亡日期是否可以修改
     * 
     * @param apNo
     */
    @DaoFieldList("APNO")
    public Boolean isEvtDieDateUpdatableForDisabledPayee(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.isEvtDieDateUpdatableForDisabledPayee", map);
        // 檢查BADAPR裡其他繼承人是否有已核付的資料,count > 0表示已有人核付,因此回傳不可修改
        if (count != null && count > 0) {
            return new Boolean(false);
        }
        if (count != null && count == 0) {
            return new Boolean(true);
        }
        return new Boolean(false);
    }

    /**
     * 依傳入條件刪除 繼承人資料 For 失能年金受款人資料更正作業
     * 
     * @param apNo
     */
    @DaoFieldList("APNO")
    public void deleteDisabledPayeeForCleanEvtDieDate(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        getSqlMapClientTemplate().delete("BAAPPBASE.deleteDisabledPayeeForCleanEvtDieDate", map);
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertData(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertData", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateData(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateData", baappbase);
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金受款人資料更正作業
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledPayeeDataUpdate(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertDataForDisabledPayeeDataUpdate", baappbase);
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 遺屬年金繼承人資料更正作業
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForSurvivorPayeeInheritData(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertDataForSurvivorPayeeInheritData", baappbase);
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 遺屬年金繼承人資料更正作業
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPayeeDataUpdateDetail(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertDataForPayeeDataUpdateDetail", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料 for 遺屬年金繼承人資料更正作業
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForSurvivorPayeeInheritData(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForSurvivorPayeeInheritData", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForSurvivorPayeeDataUpdate(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForSurvivorPayeeDataUpdate", baappbase);
    }

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteData(BigDecimal baappbaseId, String procStat) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("procStat", procStat);
        getSqlMapClientTemplate().update("BAAPPBASE.deleteData", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 行政支援記錄作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public Baappbase selectExecutiveSupportQueryBy(String apNo) {
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectExecutiveSupportQueryBy", apNo);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 行政支援維護作業
     * 
     * @param APNO 受理編號
     * @param ISSUYM 核定年月
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,ISSUYM")
    public Baappbase selectExecutiveSupportMaintBy(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectExecutiveSupportMaintBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 行政支援銷案作業
     * 
     * @param APNO 受理編號
     * @param ISSUYM 核定年月
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,ISSUYM")
    public Baappbase selectExecutiveSupportMaintForCloseBy(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectExecutiveSupportMaintForCloseBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) BAAPPBASEID
     * 
     * @param APNO 受理編號
     * @return <code>List</code> 物件
     */
    @DaoFieldList("APNO")
    public List<BigDecimal> selectBaappbaseIdBy(String apno) {
        return (List<BigDecimal>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBaappbaseIdBy", apno);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) caseTyp欄位資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateCaseTyp(BigDecimal baappbaseId, String caseTyp, String updUser, String updTime) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("caseTyp", caseTyp);
        map.put("updUser", updUser);
        map.put("updTime", updTime);
        getSqlMapClientTemplate().update("BAAPPBASE.updateCaseTyp", map);
    }

    /**
     * 取得 SEQUENCE BAAPNO
     */
    public String getSequenceApNo() {
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSequenceApNo", null);
    }

    /**
     * 取得 SEQUENCE BAKAPNO
     */
    public String getSequenceKApNo() {
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSequenceKApNo", null);
    }

    /**
     * 取得 SEQUENCE BASAPNO
     */
    public String getSequenceSApNo() {
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSequenceSApNo", null);
    }

    /**
     * 更新通訊資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForCommunication(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForCommunication", baappbase);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayment(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForPayment", baappbase);
    }

    /**
     * 更新案件資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForApplication(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForApplication", baappbase);
    }

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
    public void cancelForApplication(BigDecimal baappbaseId, String caseMk, String procStat, String updUser, String updTime, String[] upProcStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("caseMk", caseMk);
        map.put("procStat", procStat);
        map.put("updUser", updUser);
        map.put("updTime", updTime);
        if (upProcStat != null && upProcStat.length > 0)
            map.put("upProcStat", upProcStat);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);
        getSqlMapClientTemplate().update("BAAPPBASE.cancelForApplication", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 債務繼承人資料登錄作業
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectInheritorRsgisterBy(String apno) {
        return (List<Baappbase>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectInheritorRsgisterBy", apno);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 債務繼承人資料登錄作業
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    @DaoFieldList("APNO")
    public String selectSeqNoBy(String apNo) {
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectSeqNoBy", apNo);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 債務繼承人資料
     * 
     * @param APNO 受理編號
     * @param baappbaseId 資料列編號
     * @return <code>BigDecimal</code> 物件
     */
    @DaoFieldList("APNO,BAAPPBASEID")
    public Baappbase selectInheritorInfo(String apNo, String baappbaseId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("baappbaseId", baappbaseId);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectInheritorInfo", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 債務繼承人資料修改作業
     * 
     * @param apno 受理編號
     * @param benName 繼承人姓名
     * @param benIdnNo 繼承人身分證號
     * @param benBrDate 繼承人出生日期
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,BENNAME,BENIDNNO,BENBRDATE")
    public List<Baappbase> selectInheritorRsgisterForModify(String apno, String benName, String benIdnNo, String benBrDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apno", apno);
        if (StringUtils.isNotBlank(benName))
            map.put("benName", benName);
        if (StringUtils.isNotBlank(benIdnNo))
            map.put("benIdnNo", benIdnNo);
        if (StringUtils.isNotBlank(benBrDate))
            map.put("benBrDate", benBrDate);
        return (List<Baappbase>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectInheritorRsgisterForModify", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 老年、失能結案狀態變更作業
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectPayeeListForOldAgeAndDisabledCloseStatusAlteration(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        return (List<Baappbase>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPayeeListForOldAgeAndDisabledCloseStatusAlteration", map);
    }
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 遺屬結案狀態變更作業
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectPayeeListForCloseStatusAlteration(String apNo, String kind) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("kind", kind);
        return (List<Baappbase>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPayeeListForCloseStatusAlteration", map);
    }

    /**
     * 取得 受款人筆數 for 老年、失能結案狀態變更作業
     */
    @DaoFieldList("APNO")
    public String getPayeeDataCountForOldAgeAndDisabledCloseStatusAlteration(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataCountForOldAgeAndDisabledCloseStatusAlteration", map);
    }
    
    /**
     * 取得 受款人筆數 for 遺屬結案狀態變更作業
     */
    @DaoFieldList("APNO")
    public String getPayeeDataCountForCloseStatusAlteration(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataCountForCloseStatusAlteration", map);
    }

    /**
     * 更新繼承人資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForInheritorRegister(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForInheritorRegister", baappbase);
    }

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
    public void updateProcStat(BigDecimal baappbaseId, String procStat, String updUser, String updTime, String[] upProcStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("procStat", procStat);
        map.put("updUser", updUser);
        map.put("updTime", updTime);
        if (upProcStat != null && upProcStat.length > 0)
            map.put("upProcStat", upProcStat);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);
        getSqlMapClientTemplate().update("BAAPPBASE.updateProcStat", map);
    }

    /**
     * 更新合格註記 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     * @param updUser 異動者代號
     * @param updTime 異動日期時間
     */
    public void updateManchkMk(String apNo, String updUser, String updTime) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("updUser", updUser);
        map.put("updTime", updTime);
        getSqlMapClientTemplate().update("BAAPPBASE.updateManchkMk", map);
    }

    /**
     * 取得 受款人筆數
     */
    @DaoFieldList("APNO,BENIDNNO,BENBRDATE")
    public String getPayeeDataCount(String apNo, String benIdnNo, String benBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("benIdnNo", benIdnNo);
        map.put("benBrDate", benBrDate);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataCount", map);
    }

    /**
     * 取得 死掉的受款人筆數
     */
    @DaoFieldList("APNO,BENIDNNO,BENBRDATE")
    public String getDeadPayeeDataCount(String apNo, String benIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("benIdnNo", benIdnNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getDeadPayeeDataCount", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<BaappbaseDataUpdateCase> selectPayeeDataForInsert(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPayeeDataForInsert", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<BaappbaseDataUpdateCase> selectPayeeDataForList(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPayeeDataForList", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 更正作業 - 死亡改匯 - 繼承人維護
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<BaappbaseDataUpdateCase> selectPayeeDataListForOldAgeDeathRepayBy(String apNo, String heirSeqNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(heirSeqNo))
            map.put("heirSeqNo", heirSeqNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPayeeDataListForOldAgeDeathRepayBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<BaappbaseDataUpdateCase> selectDisabledPayeeDataForList(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDisabledPayeeDataForList", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<SurvivorPayeeDataUpdateCase> selectSurvivorPayeeDataForList(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorPayeeDataForList", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @param benIds 受益人社福識別碼
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappbase getPayeeDataUpdateForUpdateCaseBy(String baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(baappbaseId))
            map.put("baappbaseId", baappbaseId);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectPayeeDataUpdateForUpdateDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     * @param benIds 受益人社福識別碼
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappbase getDisabledPayeeDataUpdateForUpdateCaseBy(String baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(baappbaseId))
            map.put("baappbaseId", baappbaseId);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectPayeeDataUpdateForUpdateDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<BaappbaseDataUpdateCase> selectPayeeDataForMustIssueAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPayeeDataForMustIssueAmt", map);
    }

    /**
     * 取得 受款人筆數
     */
    @DaoFieldList("APNO,BENIDNNO,BENBRDATE,BAAPPBASEID")
    public String getPayeeDataCountForUpdate(String apNo, String benIdnNo, String benBrDate, String baappbaseId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("benIdnNo", benIdnNo);
        map.put("benBrDate", benBrDate);
        map.put("baappbaseId", baappbaseId);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataCountForUpdate", map);
    }

    /**
     * 取得 【改匯註銷】按鈕的狀態及使用條件(是否開啟的條件)DataCount1 For Update
     */
    @DaoFieldList("APNO,SEQNO")
    public String getDataCount1ForQuery(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getDataCount1ForQuery", map);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateForOldAgeDeathRepayDoCancel(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateForOldAgeDeathRepayDoCancel", baappbase);
    }

    /**
     * 取得 受款人筆數
     */
    @DaoFieldList("APNO,BENIDNNO,BENBRDATE,BAAPPBASEID")
    public String getPayeeDataCountForDisabledUpdate(String apNo, String benIdnNo, String benBrDate, String baappbaseId, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("benIdnNo", benIdnNo);
        map.put("benBrDate", benBrDate);
        map.put("baappbaseId", baappbaseId);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataCountForDisabledUpdate", map);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeData(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForPayeeData", baappbase);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForSurvivorPayeeData(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForSurvivorPayeeData", baappbase);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeDataAll(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForPayeeDataAll", baappbase);
    }

    /**
     * 取得 受款人SEQNO
     */
    @DaoFieldList("APNO")
    public String getPayeeDataForSeqNoOne(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataForSeqNoOne", map);
    }

    /**
     * 取得 受款人SEQNO
     */
    @DaoFieldList("APNO")
    public String getPayeeDataForSeqNoTwo(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataForSeqNoTwo", map);
    }

    /**
     * 取得 受款人SEQNO
     */
    @DaoFieldList("APNO")
    public String getPayeeDataForSeqNoThree(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataForSeqNoThree", map);
    }

    /**
     * 取得 受款人SEQNO
     */
    @DaoFieldList("APNO")
    public String getPayeeDataForSeqNoFour(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeDataForSeqNoFour", map);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeUpdateData(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForPayeeUpdateData", baappbase);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForOldAgeDeathRepayPayeeUpdateData(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForOldAgeDeathRepayPayeeUpdateData", baappbase);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDisabledPayeeDataUpdate(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForDisabledPayeeDataUpdate", baappbase);
    }

    /**
     * 更新給付資料 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForPayeeDataMustIssueAmt(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForPayeeDataMustIssueAmt", baappbase);
    }

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
    @DaoFieldList("APNO,SEQNO,PROCSTAT,EQTYP,PAYKIND,PAGEPAYKIND")
    public List<Baappbase> selectDataForReviewBy(String apNo, String seqNo, String[] procStat, String eqType, String[] payKind, String eqType2, String pagePayKind) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        eqType2 = StringUtility.changOperator(eqType2);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("procStat", procStat);
        map.put("eqType", eqType);
        map.put("payKind", payKind);
        if (StringUtils.isNotBlank(eqType2))
            map.put("eqType2", eqType2);
        if (pagePayKind != null && pagePayKind.length() > 0)
            map.put("pagePayKind", pagePayKind);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDataForReviewBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人清單 for 給付審核
     * 
     * @param apNo 受理編號
     * @param benPayMk 受益人領取狀態註記
     * @return 內含 <code>String</code> 物件的 List
     */
    @DaoFieldList("APNO,BENPAYMK")
    public List<String> selectBenNameBy(String apNo, String benPayMk) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("benPayMk", benPayMk);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBenNameBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 一次給付資料 for 給付審核
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectOncePayDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOncePayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 審核管制條件
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectReviewCond(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectReviewCond", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectBeneficiaryDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBeneficiaryDataBy", map);
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人核定資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,SEQNO,PROCSTAT,EQTYP")
    public List<Baappbase> selectBenIssueDataBy(String apNo, String seqNo, String[] procStat, String eqType) {
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("procStat", procStat);
        map.put("eqType", eqType);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBenIssueDataBy", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 給付審核
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForReview(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForReview", baappbase);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    @DaoFieldList("APNO_BEGIN,APNO_END")
    public List<String> getOldAgeReviewRpt01ApNoListBy(String apNoBegin, String apNoEnd) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNoBegin))
            map.put("apNoBegin", apNoBegin);
        if (StringUtils.isNotBlank(apNoEnd))
            map.put("apNoEnd", apNoEnd);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOldAgeReviewRpt01ApNoListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectOldAgeReviewRpt01EvtDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        map.put("seqNo", "0000");

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectOldAgeReviewRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金平均薪資
     * 
     * @param apNo 受理編號
     * @return caseObj
     */
    @DaoFieldList("APNO")
    public Baappbase selectDataForSurvivorAvgAmtDetail(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        map.put("seqNo", "0000");

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataForSurvivorAvgAmtDetail", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 死亡改匯繼承人維護作業
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectDataForPayeeDataUpdateDetail(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        map.put("seqNo", "0000");

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataForPayeeDataUpdateDetail", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 受款者資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectOldAgeReviewRpt01BenListBy(String apNo) {
        // 修改FORTIFY
        String eqType = StringUtility.changOperator("<>");

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        map.put("seqNo", "0000");
        map.put("eqType", eqType);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOldAgeReviewRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> getOldAgeReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getOldAgeReviewRpt01AnnuityPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 失能年金紀錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    @DaoFieldList("EVTIDNNO")
    public List<Baappbase> getOldAgeReviewRpt01DisableAnnuityPayListBy(String apNo, String evtIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getOldAgeReviewRpt01DisableAnnuityPayDataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬年金紀錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    @DaoFieldList("EVTIDNNO")
    public List<Baappbase> getOldAgeReviewRpt01SurvivorAnnuityPayListBy(String evtIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getOldAgeReviewRpt01SurvivorAnnuityPayDataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 另案扣減資料 - 年金給付 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public String getOldAgeReviewRpt01DeductAnnuityNameBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getOldAgeReviewRpt01DeductAnnuityNameBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞工紓困貸款繳納本息情形查詢單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectDataUpdateRpt01DataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataUpdateRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 紓困貸款撥款情形查詢清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectDataUpdateRpt02DataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataUpdateRpt02DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 紓困貸款抵銷情形照會單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectDataUpdateRpt03DataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataUpdateRpt03DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) for 歸檔清單
     * 
     * @param payCode 給付別
     * @param decisionSdate 決行日期起日
     * @param decisionEdate 決行日期迄日
     * @return
     */
    @DaoFieldList("PAYCODE,DECISIONSDATE,DECISIONEDATE")
    public List<Baappbase> selectDecisionRpt01ListBy(String payCode, String decisionSdate, String decisionEdate, String exeMan) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(decisionSdate))
            map.put("decisionSdate", decisionSdate);
        if (StringUtils.isNotBlank(decisionEdate))
            map.put("decisionEdate", decisionEdate);
        if (StringUtils.isNotBlank(exeMan))
            map.put("exeMan", exeMan);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDecisionRpt01ListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) for 歸檔清單（遺屬）
     * 
     * @param payCode 給付別
     * @param decisionSdate 決行日期起日
     * @param decisionEdate 決行日期迄日
     * @return
     */
    @DaoFieldList("PAYCODE,DECISIONSDATE,DECISIONEDATE")
    public List<Baappbase> selectDecisionRpt01ListForSBy(String payCode, String decisionSdate, String decisionEdate, String exeMan) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(decisionSdate))
            map.put("decisionSdate", decisionSdate);
        if (StringUtils.isNotBlank(decisionEdate))
            map.put("decisionEdate", decisionEdate);
        if (StringUtils.isNotBlank(exeMan))
            map.put("exeMan", exeMan);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDecisionRpt01ListForSBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 調卷/還卷單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectOtherRpt01DataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectOtherRpt01DataBy", map);
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 給付決行資料 By 受理編號
     * 
     * @param apNo 受理編號
     * @param empId 員工編號
     * @param payCode 給付別
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,EMPID,PAYCODE")
    public List<Baappbase> selectDecisionDataByApNo(String apNo, String empId, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("empId", empId);
        map.put("payCode", payCode);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDecisionDataByApNo", map);
    }

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
    @DaoFieldList("RPTDATE,PAGENO,CHKMAN,EMPID,PAYCODE")
    public List<Baappbase> selectDecisionDataByRptDate(String rptDate, BigDecimal pageNo, String chkMan, String empId, String payCode) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rptDate", rptDate);
        map.put("pageNo", pageNo);
        map.put("chkMan", chkMan);
        map.put("empId", empId);
        map.put("payCode", payCode);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDecisionDataByRptDate", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 給付決行
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDecision(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForDecision", baappbase);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢MasterData
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
    @DaoFieldList("APNO,BENIDNNO,BENNAME,BENBRDATE,QRYCOND,STARTYM,ENDYM")
    public List<Baappbase> selectPaymentQueryMasterDataBy(String apNo, String benIdnNo, String benName, String benBrDate, String qryCond, String startYm, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(benIdnNo)) {
            map.put("benIdnNo", benIdnNo);
        }
        if (StringUtils.isNotBlank(benName)) {
            map.put("benName", benName);
        }
        if (StringUtils.isNotBlank(benBrDate)) {
            map.put("benBrDate", benBrDate);
        }
        map.put("qryCond", qryCond);
        if (StringUtils.isNotBlank(startYm)) {
            map.put("startYm", startYm);
        }
        if (StringUtils.isNotBlank(endYm)) {
            map.put("endYm", endYm);
        }
        // 如果姓名、身分證號、出生日期都未輸入，則必須將SEQNO設為'0000'，避免同一案件出現多筆資料
        if (StringUtils.isBlank(benIdnNo) && StringUtils.isBlank(benName) && StringUtils.isBlank(benBrDate)) {
            map.put("seqNo", "0000");
        }

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPaymentQueryMasterDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢MasterDetail
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @param payKind 給付別
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,PAYKIND,PAGEPAYKIND")
    public List<Baappbase> selectPaymentQueryDetail(String apNo, String payKind, String pagePayKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(payKind)) {
            map.put("payKind", payKind);
        }
        map.put("pagePayKind", pagePayKind);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPaymentQueryDetail", map);
    }

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
    @DaoFieldList("APNO,SEQNO,QRYCOND,STARTYM,ENDYM")
    public List<Baappbase> selectBenIssuDataForPaymentQueryBy(String apNo, String seqNo, String qryCond, String startYm, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("qryCond", qryCond);
        map.put("startYm", startYm);
        map.put("endYm", endYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBenIssuDataForPaymentQueryBy", map);
    }

    /**
     * 更正作業報表 止付單 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectDataUpdateRpt05ForBaappbase(String apNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectPayeeDataForMustIssueAmt", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 月處理核定合格清冊 for 列印作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt01Report(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt01Report", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 紓困貸款呆帳債務人照會單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectDataUpdateRpt04DataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDataUpdateRpt04DataBy", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectMonthlyRpt05Report(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05Report", apNo);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectMonthlyRpt05Report2(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05Report2", apNo);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectMonthlyRpt05Report3(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05Report3", apNo);
    }
 
    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectMonthlyRpt05ForSurvivorReport(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05ForSurvivorReport", apNo);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectMonthlyRpt05PrintDataForSurvivorReport(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintDataForSurvivorReport", apNo);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataForSurvivorReport(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintCase2DataForSurvivorReport", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<String> selectMonthlyRpt05ReportCopy1(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05ReportCopy1", apNo);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<String> selectMonthlyRpt05ReportCopy2(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05ReportCopy2", apNo);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 給付審核
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> selectAnnuityPayDataBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("evtIdnNo", evtIdnNo);
        map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectAnnuityPayDataBy", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 一次給付資料更正-轉公保資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForOnceAmtDataUpdate(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForOnceAmtDataUpdate", baappbase);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) for 一次給付資料更正-轉公保資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public Baappbase selectDataForOnceAmtDataUpdateByLog(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataForOnceAmtDataUpdateByLog", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 核定清單 for 列印作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt06Report(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt06Report", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 另案扣減照會單 for 列印作業
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public Baappbase selectOldAgeReview04Report(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectOldAgeReviewRpt04Report", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 案件資料更正-事故者姓名資料 for 事故者
     * 
     * @param baappbase 給付主檔
     */
    public void updateEvtDataForApplicationUpdate(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateEvtDataForApplicationUpdate", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 案件資料更正-事故者姓名資料 for 受款人
     * 
     * @param baappbase 給付主檔
     */
    public void updateBenDataForApplicationUpdate(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateBenDataForApplicationUpdate", baappbase);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public Integer selectForPayeeDataUpdateQ2(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectForPayeeDataUpdateQ2", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public Integer selectForPayeeDataUpdateRelationQ1(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectForPayeeDataUpdateRelationQ1", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public Integer selectForPayeeDataUpdateRelationQ2(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectForPayeeDataUpdateRelationQ2", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public Integer selectForPayeeDataUpdateRelationQ3(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectForPayeeDataUpdateRelationQ3", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 關係欄位控制
     * 
     * @param brDate 事故者出生日期
     * @param idnNo 事故者身份字號
     */
    @DaoFieldList("BRDATE,IDNNO")
    public Integer selectForPayeeDataUpdateRelationQ4(String brDate, String idnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("brDate", brDate);
        map.put("idnNo", idnNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectForPayeeDataUpdateRelationQ4", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 受款人資料更正 刪除按鈕控制
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO,SEQNO")
    public Integer selectForPayeeDataUpdateQueryQ4(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectForPayeeDataUpdateQueryQ4", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者存活狀態
     * 
     * @param apNo 受理編號
     * @return <code>Integer</code> 物件
     */
    @DaoFieldList("APNO")
    public Integer selectEvtAliveStatusBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectEvtAliveStatusBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 被具名領取筆數
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Integer</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Integer selectAccSeqNoAmt(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectAccSeqNoAmt", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 帳號資料
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,ACCSEQNO")
    public Baappbase selectBankDataBy(BigDecimal baappbaseId, String accSeqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("accSeqNo", accSeqNo);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectBankDataBy", map);
    }

    /**
     * 依傳入條件刪除 給付主檔(<code>BAAPPBASE</code>) 債務繼承人資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappbase</code> 物件
     */
    public void deleteForInheritorRegister(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);

        if (seqNo.length() == 4) {
            if (("0").equals(seqNo.substring(0, 1)) && !("0").equals(seqNo.substring(1, 2)) && ("0").equals(seqNo.substring(2, 3)) && ("0").equals(seqNo.substring(3, 4))) {
                map.put("seqNo", seqNo.substring(0, 2));
                map.put("eqType", "like");
            }
            else if (("0").equals(seqNo.substring(0, 1)) && !("0").equals(seqNo.substring(1, 2)) && !("0").equals(seqNo.substring(2, 3)) && ("0").equals(seqNo.substring(3, 4))) {
                map.put("seqNo", seqNo.substring(0, 3));
                map.put("eqType", "like");
            }
            else if (("0").equals(seqNo.substring(0, 1)) && !("0").equals(seqNo.substring(1, 2)) && !("0").equals(seqNo.substring(2, 3)) && !("0").equals(seqNo.substring(3, 4))) {
                map.put("seqNo", seqNo);
                map.put("eqType", null);
            }
            else {
                map.put("seqNo", seqNo);
                map.put("eqType", null);
            }
        }
        else {
            map.put("seqNo", seqNo);
            map.put("eqType", null);
        }

        getSqlMapClientTemplate().delete("BAAPPBASE.deleteForInheritorRegister", map);
    }

    /**
     * 刪除 給付主檔 (<code>BAAPPBASE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteBaappbaseData(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        getSqlMapClientTemplate().delete("BAAPPBASE.deleteBaappbaseData", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public List<Baappbase> selectBaappbaseDataByLog(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBaappbaseDataByLog", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 受款人資料更正 刪除受款人資料後更新
     * 
     * @param baappbase 給付主檔
     */
    public void updateProcstatForPayeeDataUpdate(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateProcstatForPayeeDataUpdate", baappbase);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 案件資料更正"註銷"按鈕狀態
     * 
     * @param apNo 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    @DaoFieldList("APNO")
    public BigDecimal checkDelBtnForPaymentUpdate(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAAPPBASE.checkDelBtnForPaymentUpdate", map);
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金受理作業
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledAnnuityReceipt(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertDataForDisabledAnnuityReceipt", baappbase);
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金受理作業 國保資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataDisabledAnnuityReceiptFor36(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertDataDisabledAnnuityReceiptFor36", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金給付資料 事故者資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDisabledAnnuityReceipt(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForDisabledAnnuityReceipt", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金給付資料 眷屬資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDisabledDetail(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForDisabledDetail", baappbase);
    }

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 資料 for 失能年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    public void deleteDataForDisabledAnnuityReceipt(BigDecimal baappbaseId, String procStat) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("procStat", procStat);
        getSqlMapClientTemplate().update("BAAPPBASE.deleteDataForDisabledAnnuityReceipt", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 失能年金給付受理眷屬清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectBenDataListBy(String apNo) {
        // 修改FORTIFY
        String eqType = StringUtility.changOperator("<>");
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        map.put("seqNo", "0000");
        map.put("eqType", eqType);

        return (List<Baappbase>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBenDataForDisabled", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料 for 失能年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,SEQNO,PROCSTAT,EQTYPE")
    public Baappbase selectDetailDataForDisabledAnnuityReceiptBy(BigDecimal baappbaseId, String seqNo, String[] procStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDetailDataForDisabledAnnuityReceiptBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料 for 遺屬年金受理作業
     * 
     * @param baappbaseId 資料列編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,SEQNO,PROCSTAT,EQTYPE")
    public Baappbase selectDetailDataForSurvivorAnnuityReceiptBy(BigDecimal baappbaseId, String seqNo, String[] procStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDetailDataForSurvivorAnnuityReceiptBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 詳細資料清單 for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含<code>Baappbase</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO,PROCSTAT,EQTYPE")
    public List<Baappbase> selectDetailDataListForSurvivorAnnuityReceiptBy(String apNo, String seqNo, String[] procStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDetailDataListForSurvivorAnnuityReceiptBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 失能/遺屬年金受理作業
     * 
     * @param evtIdnNo 事故者身分證號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @param payKind 給付種類
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("EVIDNNO,APNO,SEQNO,PROCSTAT,EQTYPE,PAYKIND")
    public List<Baappbase> selectDataForAnnuityReceiptBy(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqType, String payKind) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(evtIdnNo)) {
            map.put("evtIdnNo", evtIdnNo);
        }
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        if (StringUtils.isNotBlank(payKind)) {
            map.put("payKind", payKind);
        }
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDataForAnnuityReceiptBy", map);
    }

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
    @DaoFieldList("EVIDNNO,APNO,SEQNO,PROCSTAT,EQTYP")
    public List<Baappbase> selectSurvivorEvtDataListBy(String evtIdnNo, String apNo, String seqNo, String[] procStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(evtIdnNo)) {
            map.put("evtIdnNo", evtIdnNo);
        }
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorEvtDataListBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬資料清單 for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,PROCSTAT,EQTYP")
    public List<Baappbase> selectSurvivorBenDataListBy(String apNo, String[] procStat, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        if (procStat != null && procStat.length > 0) {
            map.put("procStat", procStat);
        }
        if (StringUtils.isNotBlank(eqType)) {
            map.put("eqType", eqType);
        }
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorBenDataListBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 判斷確認按鈕狀態條件 for 給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    @DaoFieldList("APNO")
    public BigDecimal checkBtnStatusForPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAAPPBASE.checkBtnStatusForPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 序號 for 給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    @DaoFieldList("APNO")
    public String getNewSeqNoForSurvivorAnnuity(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getNewSeqNoForSurvivorAnnuity", map);
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 事故者資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertEvtDataForSurvivorAnnuityReceipt(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertEvtDataForSurvivorAnnuityReceipt", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 事故者資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateEvtDataForSurvivorAnnuityReceipt", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 事故者資料
     * 
     * @param baappbaseList 給付主檔
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(final List<Object> baappbaseList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (int i = 0; i < baappbaseList.size(); i++) {
                    Baappbase baappbase = (Baappbase) baappbaseList.get(i);
                    executor.update("BAAPPBASE.updateEvtDataForSurvivorAnnuityReceipt", baappbase);
                }
                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 新增 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 遺屬資料
     * 
     * @param baappbase 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBenDataForSurvivorAnnuityReceipt(Baappbase baappbase) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAAPPBASE.insertBenDataForSurvivorAnnuityReceipt", baappbase);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 遺屬年金受理作業 遺屬資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateBenDataForSurvivorAnnuityReceipt(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateBenDataForSurvivorAnnuityReceipt", baappbase);
    }

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 受理資料 By BAAPPBASEID for 遺屬年金受理作業
     * 
     * @param baappbase 給付主檔
     */
    public void deleteDataForSurvivorAnnuityReceiptById(BigDecimal baappbaseId, String procStat) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("procStat", procStat);
        getSqlMapClientTemplate().delete("BAAPPBASE.deleteDataForSurvivorAnnuityReceiptById", map);
    }

    /**
     * 刪除 給付主檔(<code>BAAPPBASE</code>) 所有受理資料 By APNO for 遺屬年金受理作業
     * 
     * @param apNo 受理編號
     */
    public void deleteAllDataForSurvivorAnnuityReceiptByApNo(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        getSqlMapClientTemplate().delete("BAAPPBASE.deleteAllDataForSurvivorAnnuityReceiptByApNo", map);
    }

    /**
     * 依入條件取得 給付主檔(<code>BAAPPBASE</code>) 「事故者姓名」欄位是否可以修改 for 案件資料更正
     * 
     * @param apNo 受理編號
     * @return <code>BigDecimal</code>
     */
    @DaoFieldList("APNO")
    public BigDecimal selectEvtNameModifyStatusForApplicationUpdate(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectEvtNameModifyStatusForApplicationUpdate", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保老年年金給 另案扣減照會單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectOldAgeReviewRpt04DataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        // return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectOldAgeReviewRpt04DataBy", map);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectOldAgeReviewRpt04Report", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 具名領取 資料列編號
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<BigDecimal> selectBaappbaseIdByAccSeqNo(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBaappbaseIdByAccSeqNo", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 帳號資料
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,ACCSEQNO")
    public Baappbase selectBankDataByAccSeqNo(String apNo, String accSeqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("accSeqNo", accSeqNo);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectBankDataByAccSeqNo", map);
    }

    /**
     * 依傳入條件取得 處理函別(<code>BAAPPBASE</code>) 更新條件
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer getProCstatUpdateLetterType(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getProCstatUpdateLetterType", map);
    }

    /**
     * 依傳入條件 (<code>BAAPPBASE</code>) 取得 按鈕狀態的資料
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Integer getButtonStatus(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getButtonStatus", map);
    }

    /**
     * 依傳入的條件取得 是否為已註銷 (<code>BAAPPBASE</code>) 的案件資料<br>
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Integer getProcastatStatus(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getProcastatStatus", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 處理狀態
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>String</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public String selectProcStatBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectProcStatBy", map);
    }

    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public void updateForCheckMarkLevelAdjust(String apNo) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateForCheckMarkLevelAdjust", apNo);
    }

    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public void updateCloseDateForCheckMarkLevelAdjust(String apNo) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateCloseDateForCheckMarkLevelAdjust", apNo);
    }

    /**
     * 依傳入條件取得(<code>BAAPPBASE</code>) FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     */
    public Baappbase selectForCheckMarkLevelAdjustByLog(String apno) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apno", apno);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectForCheckMarkLevelAdjustByLog", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 for 給付審核
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public Integer selectDataCountForReviewUpdate(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataCountForReviewUpdate", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金編審註記程度調整 (Header 資料)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getDisabledCheckMarkLevelAdjustHeaderDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getDisabledCheckMarkLevelAdjustHeaderDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金編審註記程度調整 (受款人資料)
     * 
     * @param apNo
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> getDisabledCheckMarkLevelAdjustBenListBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisabledCheckMarkLevelAdjustBenListBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金編審註記程度調整 (Header 資料)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getSurvivorCheckMarkLevelAdjustHeaderDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSurvivorCheckMarkLevelAdjustHeaderDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金編審註記程度調整 (受款人資料)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> getSurvivorCheckMarkLevelAdjustBenListBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getSurvivorCheckMarkLevelAdjustBenListBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getDisabledApplicationDataUpdateMasterDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        List<Baappbase> list = getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisabledApplicationDataUpdateMasterDataBy", map);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 給付查詢重新查核失能程度
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getDisabledApplicationDataUpdateMasterDataForRehcBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        List<Baappbase> list = getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisabledApplicationDataUpdateMasterDataForRehcBy", map);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (是否顯示 註銷 按鍵)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則顯示
     */
    @DaoFieldList("APNO")
    public Integer getDisabledApplicationDataUpdateIsShowCancelButtonBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getDisabledApplicationDataUpdateIsShowCancelButtonBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (事故者姓名 可否修改)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則可修改
     */
    @DaoFieldList("APNO")
    public Integer getDisabledApplicationDataUpdateCanModifyEvtNameBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getDisabledApplicationDataUpdateCanModifyEvtNameBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @return <code>BAAPPBASEID</code> 資料列編號
     */
    @DaoFieldList("APNO")
    public List<BigDecimal> getDisabledApplicationDataUpdateListForUpdateBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisabledApplicationDataUpdateListForUpdateBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (依指定的資料列編號取得須更新的給付主檔資料)
     * 
     * @param baappbaseId 資料列編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappbase getDisabledApplicationDataUpdateMasterDataByBaappbaseId(BigDecimal baappbaseId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappbaseId", baappbaseId);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getDisabledApplicationDataUpdateMasterDataByBaappbaseId", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正
     * 
     * @param dataList
     */
    public void updateDisabledApplicationDataUpdateData(final List<Baappbase> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappbase data = (Baappbase) dataList.get(i);
                    executor.update("BAAPPBASE.updateDisabledApplicationDataUpdateData", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (身分證重號時)
     * 
     * @param dataList
     */
    public void updateDisabledApplicationDataUpdateDupeIdnData(final List<Baappbase> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappbase data = (Baappbase) dataList.get(i);
                    executor.update("BAAPPBASE.updateDisabledApplicationDataUpdateDupeIdnData", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (須註銷的資料列編號)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<BigDecimal> getDisabledApplicationDataUpdateListForDeleteBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisabledApplicationDataUpdateListForDeleteBy", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 失能年金案件資料更正 (註銷案件)
     * 
     * @param apNo 受理編號
     * @param userData
     */
    public void deleteDisabledApplicationDataUpdateData(String apNo, UserBean userData) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);
        map.put("updUser", userData.getEmpNo());
        map.put("updTime", DateUtility.getNowWestDateTime(true));

        getSqlMapClientTemplate().update("BAAPPBASE.deleteDisabledApplicationDataUpdateData", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getSurvivorApplicationDataUpdateMasterDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        List<Baappbase> list = getSqlMapClientTemplate().queryForList("BAAPPBASE.getSurvivorApplicationDataUpdateMasterDataBy", map);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (是否顯示 註銷 按鍵)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則顯示
     */
    @DaoFieldList("APNO")
    public Integer getSurvivorApplicationDataUpdateIsShowCancelButtonBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSurvivorApplicationDataUpdateIsShowCancelButtonBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (事故者姓名 可否修改)
     * 
     * @param apNo 受理編號
     * @return 大於 0 則可修改
     */
    @DaoFieldList("APNO")
    public Integer getSurvivorApplicationDataUpdateCanModifyEvtNameBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSurvivorApplicationDataUpdateCanModifyEvtNameBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @return <code>BAAPPBASEID</code> 資料列編號
     */
    @DaoFieldList("APNO")
    public List<BigDecimal> getSurvivorApplicationDataUpdateListForUpdateBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getSurvivorApplicationDataUpdateListForUpdateBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (依指定的資料列編號取得須更新的給付主檔資料)
     * 
     * @param baappbaseId 資料列編號
     * @return
     */
    @DaoFieldList("BAAPPBASEID")
    public Baappbase getSurvivorApplicationDataUpdateMasterDataByBaappbaseId(BigDecimal baappbaseId) {
        HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();

        map.put("baappbaseId", baappbaseId);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSurvivorApplicationDataUpdateMasterDataByBaappbaseId", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正
     * 
     * @param dataList
     */
    public void updateSurvivorApplicationDataUpdateData(final List<Baappbase> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappbase data = (Baappbase) dataList.get(i);
                    executor.update("BAAPPBASE.updateSurvivorApplicationDataUpdateData", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (身分證重號時)
     * 
     * @param dataList
     */
    public void updateSurvivorApplicationDataUpdateDupeIdnData(final List<Baappbase> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappbase data = (Baappbase) dataList.get(i);
                    executor.update("BAAPPBASE.updateSurvivorApplicationDataUpdateDupeIdnData", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (須註銷的資料列編號)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<BigDecimal> getSurvivorApplicationDataUpdateListForDeleteBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getSurvivorApplicationDataUpdateListForDeleteBy", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 的資料 for 遺屬年金案件資料更正 (註銷案件)
     * 
     * @param apNo 受理編號
     * @param userData 使用者物件
     */
    public void deleteSurvivorApplicationDataUpdateData(String apNo, UserBean userData) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);
        map.put("updUser", userData.getEmpNo());
        map.put("updTime", DateUtility.getNowWestDateTime(true));

        getSqlMapClientTemplate().update("BAAPPBASE.deleteSurvivorApplicationDataUpdateData", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用受理作業 (Header for Add)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getReviewFeeReceiptHeaderDataForInsertBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getReviewFeeReceiptHeaderDataForInsertBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用受理作業 (Header for Update)
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO")
    public Baappbase getReviewFeeReceiptHeaderDataForUpdateBy(String apNo, String evtIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getReviewFeeReceiptHeaderDataForUpdateBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用審核作業
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getReviewFeeReviewHeaderDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getReviewFeeReviewHeaderDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料 for 複檢費用決行作業
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase getReviewFeeDecisionHeaderDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getReviewFeeDecisionHeaderDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的決行層級資料 for 複檢費用決行作業
     * 
     * @param apNo 受理編號
     * @param empNo 使用者員工編號
     * @return
     */
    @DaoFieldList("APNO,EMPNO")
    public Baappbase getReviewFeeDecisionExcLvlBy(String apNo, String empNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);
        map.put("empNo", empNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getReviewFeeDecisionExcLvlBy", map);

    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    @DaoFieldList("APNO_BEGIN,APNO_END")
    public List<String> getDisableReviewRpt01ApNoListBy(String apNoBegin, String apNoEnd) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNoBegin))
            map.put("apNoBegin", apNoBegin);
        if (StringUtils.isNotBlank(apNoEnd))
            map.put("apNoEnd", apNoEnd);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDisableReviewRpt01ApNoListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectDisableReviewRpt01EvtDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        map.put("seqNo", "0000");

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDisableReviewRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 受款者資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectDisableReviewRpt01BenListBy(String apNo) {
        // 修改FORTIFY
        String eqType = StringUtility.changOperator("<>");
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        map.put("seqNo", "0000");
        map.put("eqType", eqType);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDisableReviewRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> getDisableReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisableReviewRpt01AnnuityPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 申請國保身障年金給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> getDisableReviewRpt01NbPayListBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIds))
            map.put("evtIds", evtIds);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisableReviewRpt01NbPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 老年年金年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> getDisableReviewRpt01OldPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisableReviewRpt01OldPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬年金年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> getDisableReviewRpt01SurvivorPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getDisableReviewRpt01SurvivorPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNoBegin 受理編號 (起)
     * @param apNoEnd 受理編號 (迄)
     * @return
     */
    @DaoFieldList("APNO_BEGIN,APNO_END")
    public List<String> selectSurvivorReviewRpt01ApNoListBy(String apNoBegin, String apNoEnd) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNoBegin)) {
            map.put("apNoBegin", apNoBegin);
        }
        if (StringUtils.isNotBlank(apNoEnd)) {
            map.put("apNoEnd", apNoEnd);
        }
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorReviewRpt01ApNoListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Baappbase selectSurvivorReviewRpt01EvtDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        map.put("seqNo", "0000");
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectSurvivorReviewRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectSurvivorReviewRpt01BenListBy(String apNo) {
        // 修改FORTIFY
        String eqType = StringUtility.changOperator("<>");
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        map.put("seqNo", "0000");
        map.put("eqType", eqType);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorReviewRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 繼承人資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectSurvivorReviewRpt01BenListByExt(String apNo) {
        // 修改FORTIFY
        String eqType = StringUtility.changOperator("<>");
        String eqSeqType = StringUtility.changOperator("<>");
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        map.put("seqNo", "0000");
        map.put("eqType", eqType);
        map.put("eqSeqType", eqSeqType);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorReviewRpt01DataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectSurvivorRpt01BenDataList(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(apNo)) {
            map.put("seqNo", seqNo);
        }

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorRptBenDataList", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 繼承人資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectSurvivorRpt01ExtDataList(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorRptExtDataList", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 繼承人資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectSurvivorRpt01ExtRefDataList(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorRptExtRefDataList", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> selectSurvivorReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorReviewRpt01AnnuityPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> selectSurvivorReviewRpt01OldPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorReviewRpt01OldPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Baappbase> selectSurvivorReviewRpt01DisablePayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorReviewRpt01DisablePayListBy", map);
    }

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
    @DaoFieldList("APNO,BENIDNNO,BENBRDATE,BAAPPBASEID,EQTYPE")
    public List<BigDecimal> selectExistDataIdForSurvivorAnnuityReceipt(String apNo, String benIdnNo, String benBrDate, BigDecimal baappbaseId, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("benIdnNo", benIdnNo);
        map.put("benBrDate", benBrDate);
        if (baappbaseId != null)
            map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectExistDataIdForSurvivorAnnuityReceipt", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 被共同具領之受款人員序號 for 遺屬屬年金受理作業
     * 
     * @param apNo 受理編號
     * @param benIdnNo 受款人身分證號
     * @return <code>String</code> 物件
     */
    @DaoFieldList("APNO,BENIDNNO")
    public String selectAccSeqNoForSurvivorAnnuityReceipt(String apNo, String benIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("benIdnNo", benIdnNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectAccSeqNoForSurvivorAnnuityReceipt", map);
    }

    /**
     * 更新給付主檔(<code>BAAPPBASE</code>) 具領人序號 for 遺屬屬年金受理作業
     * 
     * @param accSeqNo 具領人序號
     * @param baappbaseId 資料列編號
     */
    public void updateAccSeqNoForSurvivorAnnuityReceipt(String accSeqNo, BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("accSeqNo", accSeqNo);
        map.put("baappbaseId", baappbaseId);
        getSqlMapClientTemplate().update("BAAPPBASE.updateAccSeqNoForSurvivorAnnuityReceipt", map);
    }

    /**
     * 依傳入條件取得 給付主檔繼承自誰的(<code>BAAPPBASE</code>) 資料 FOR 失能年金受款人資料修改作業
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase getAncestorInfoBySeqNo(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getAncestorInfoBySeqNo", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者/受款人資料 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectEvtBenDataForSurvivorPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectEvtBenDataForSurvivorPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 事故者/受款人核定資料 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param eqType SQL EqualType
     * @return 內含<code>Baappbase</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO,EQTYPE")
    public List<Baappbase> selectEvtBenIssuDataForSurvivorPaymentReview(String apNo, String seqNo, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("eqType", eqType);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectEvtBenIssuDataForSurvivorPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 受款人下拉選單 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectSeqNoListForSurvivorPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSeqNoListForSurvivorPaymentReview", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) FOR 遺屬年金給付審核
     * 
     * @param baappbase 給付主擋
     */
    public void updateDataForSurvivorPaymentReview(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForSurvivorPaymentReview", baappbase);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 (判斷『給付主檔』目前的處理狀態是否可以更新) FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    @DaoFieldList("APNO")
    public Integer selectDataCount1ForSurvivorPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataCount1ForSurvivorPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 (判斷目前各給付年月是否已審核完成) FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    @DaoFieldList("APNO")
    public Integer selectDataCount2ForSurvivorPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataCount2ForSurvivorPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Baappbase> selectAccDataForSurvivorAnnuityReceipt(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectAccDataForSurvivorAnnuityReceipt", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param baappbase 給付主檔
     */
    public void updateAccDataForSurvivorAnnuityReceipt(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateAccDataForSurvivorAnnuityReceipt");
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param dataList
     */
    public void updateAccDataForSurvivorAnnuityReceipt(final List<Baappbase> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappbase data = (Baappbase) dataList.get(i);
                    executor.update("BAAPPBASE.updateAccDataForSurvivorAnnuityReceipt", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 事故堵資料 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectDependantEvtDataForList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDependantEvtDataListBy", map);
    }

    /**
     * 更新 具名領取人的給付(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @param procStat 處理狀態
     */
    // 若此SQL條件有所更改，請一併修改 UpdateService.isCleanCoreceivePaymentData()
    public void cleanCoreceivePaymentData(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        getSqlMapClientTemplate().update("BAAPPBASE.cleanCoreceivePaymentData", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 立帳對象姓名 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>String</code> 物件
     */
    @DaoFieldList("APNO,BENIDNNO")
    public String selectDisablePayDeductBenname(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDisablePayDeductBenname", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectDependantDataList(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDependantDataListBy", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDependant(Baappbase baappbase) {
        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForDependantData", baappbase);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料筆數 (判斷案件資料更正是否需輸入'發放方式')
     * 
     * @param APNO 受理編號
     * @param equalTyp
     * @return <code>Integer</code> 物件
     */
    public Integer chkInterValMonthStatus(String apNo, String equalTyp) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("equalTyp", equalTyp);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.chkInterValMonthStatus", map);
    }

    /**
     * 取得 INTERVALMONTH
     */
    @DaoFieldList("APNO,SEQNO")
    public String getInterValMonth(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getInterValMonth", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) FOR 列印歸檔清單
     * 
     * @param dataList
     */
    public void updateDataForDecisionRpt01(final List<Baappbase> dataList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (int i = 0; i < dataList.size(); i++) {
                    Baappbase data = (Baappbase) dataList.get(i);
                    executor.update("BAAPPBASE.updateDataForDecisionRpt01", data);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateRegetCipbMkByApNo(String apNo, String regetCipbMk) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("regetCipbMk", regetCipbMk);
        getSqlMapClientTemplate().update("BAAPPBASE.updateRegetCipbMkByApNo", map);
    }

    // OldAge Death Repay begin
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 (檢核受款人資料是否符合可改匯處理的資格)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Baappbase> selectCheckOldAgeDeathRepayDataBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectCheckOldAgeDeathRepayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 符合資料，有退匯資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序號
     * @param heirSeqNo 繼承人序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,SEQNO,HEIRSEQNO")
    public List<Baappbase> selectOldAgeDeathRepayDataBy(String apNo, String seqNo, String heirSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(heirSeqNo))
            map.put("heirSeqNo", heirSeqNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOldAgeDeathRepayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 繼承人資料
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,HEIRSEQNO")
    public List<Baappbase> selectHeirSeqNoExistDataBy(String apNo, String heirSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(heirSeqNo))
            map.put("heirSeqNo", heirSeqNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectHeirSeqNoExistDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 退匯金額分配資料
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    @DaoFieldList("APNO,HEIRSEQNO")
    public List<Baappbase> selectRepayIssueAmtDataBy(String apNo, String heirSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(heirSeqNo))
            map.put("heirSeqNo", heirSeqNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectRepayIssueAmtDataBy", map);
    }

    /**
     * For DeathRepayData 每月取得分配人數 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 的資料<br>
     * 
     * @param apNo 受理編號
     * @param heirSeqNo 繼承人序號
     */
    @DaoFieldList("APNO,HEIRSEQNO")
    public String selectRepayIssueAmtDataCountBy(String apNo, String heirSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(heirSeqNo))
            map.put("heirSeqNo", heirSeqNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectRepayIssueAmtDataCountBy", map);
    }
    // OldAge Death Repay end

    // OldAge Payment Receive begin
    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectCashReceiveDataBy(String apNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectCashReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public Baappbase selectKCashReceiveDataBy(String apNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectKCashReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public Baappbase selectSCashReceiveDataBy(String apNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectSCashReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回註銷
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public Baappbase selectChkCancelCashReceiveDataBy(String apNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectCashReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectRemittanceReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectDisabledRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDisabledRemittanceReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-退匯收回
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectSurvivorRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectSurvivorRemittanceReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-退匯收回註銷
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectChkCancelRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectChkCancelRemittanceReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 失能年金應收收回處理作業-退匯收回註銷
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectChkCancelDisabledRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectChkCancelDisabledRemittanceReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 遺屬年金應收收回處理作業-退匯收回註銷
     * 
     * @param APNO 受理編號
     * @param SEQNO 受款人序
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Baappbase selectChkCancelSurvivorRemittanceReceiveDataBy(String apNo, String seqNo, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectChkCancelSurvivorRemittanceReceiveDataBy", map);
    }
    // OldAge Payment Receive end

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getReceiveNameBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getReceiveNameBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public List<Baappbase> getReceiveNameCase2By(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getReceiveNameCase2By", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getSingleReceiveNameBy(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSingleReceiveNameBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getSingleReceiveNameCase2By(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSingleReceiveNameCase2By", map);
    }

    /**
     * 依傳入條件取得 給付主檔 資料清單 FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @return 內含 <code>String</code> 物件的 List
     */
    public List<Baappbase> getReceiveBenNameBy(String apNo, String grdName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("grdName", grdName);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getReceiveBenNameBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 資料清單 FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @return 內含 <code>String</code> 物件的 List
     */
    public List<Baappbase> getReceiveBenNameCase2By(String apNo, String grdName) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("grdName", grdName);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getReceiveBenNameCase2By", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) SEQNO FOR 遺屬受理審核清單核定通知書受文者
     * 
     * @param APNO 受理編號
     * @return <code>BigDecimal</code> 物件
     */
    public String getReceivePayTypBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getReceivePayTypBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29DisabledMDataBy() {
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29DisabledMDataBy");
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29SurvivorMDataBy() {
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29SurvivorMDataBy");
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29DisabledYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("studeDateFormChk", studeDateFormChk);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29DisabledYDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 重新查核失能程度通知函
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30K01DataBy(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt30K01DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 通過查核續發失能年金通知函
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30K02DataBy(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt30K02DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 重新查核失能程度通知函 (EXCEL)
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30ExeclK01DataBy(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt30ExeclK01DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 通過查核續發失能年金通知函 (EXCEL)
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt30ExeclK02DataBy(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt30ExeclK02DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 批次老年差額金通知 - 第一次通知函 001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001DataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31001DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第一次通知函 001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001FDataBy(String payCode, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31001FDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 批次老年差額金通知 - 第一次通知函 002
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002DataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31002DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第一次通知函 002 和 第一次通知函003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002And31003FDataBy(String payCode, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31002And31003FDataBy", map);
    }
    
    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第一次通知函 003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31003DataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31003DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第二次通知函（催辦） 001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001UDataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31001UDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第二次通知函（催辦） 002
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002UDataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31002UDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第二次通知函（催辦） 003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31003UDataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31003UDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第三次通知函（延不補正） 001
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31001PDataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31001PDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第三次通知函（延不補正）002
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31002PDataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31002PDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 老年差額金通知 - 第三次通知函（延不補正）003
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt31003PDataBy(String payCode, String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt31003PDataBy", map);
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（1-第1次發函）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify1(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectApNoListForOtherRpt12Notify1", map);
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（2-催辦）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify2(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectApNoListForOtherRpt12Notify2", map);
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（3-延不補正）
     */
    public List<Baappbase> selectApNoListForOtherRpt12Notify3(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectApNoListForOtherRpt12Notify3", map);
    }

    /**
     * 查詢 給付主檔 (<code>BAAPPBASE</code>) 案件資料 FOR 批次老年差額金通知（全部）
     */
    public List<Baappbase> selectApNoListForOtherRpt12NotifyA(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectApNoListForOtherRpt12NotifyA", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29SurvivorYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("studeDateFormChk", studeDateFormChk);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29SurvivorYDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29DisabledY06B01DataBy(String studeChkMonth, String studeDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("apNo", apNo);
        return (List<Baappbase>) getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29DisabledY06B01DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29SurvivorY06B01DataBy(String studeChkMonth, String studeDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("apNo", apNo);
        return (List<Baappbase>) getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29SurvivorY06B01DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclDisabledMDataBy() {
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29ExeclDisabledMDataBy");
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每月 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclSurvivorMDataBy() {
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29ExeclSurvivorMDataBy");
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclDisabledYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("studeDateFormChk", studeDateFormChk);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29ExeclDisabledYDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclSurvivorYDataBy(String studeChkMonth, String studeDate, String studeDateFormChk) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("studeDateFormChk", studeDateFormChk);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29ExeclSurvivorYDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 失能
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclDisabledY06DataBy(String studeChkMonth, String studeDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29ExeclDisabledY06DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 for 補送在學證明通知函 每年 遺屬
     * 
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> getMonthlyRpt29ExeclSurvivorY06DataBy(String studeChkMonth, String studeDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("studeChkMonth", studeChkMonth);
        map.put("studeDate", studeDate);
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAAPPBASE.getMonthlyRpt29ExeclSurvivorY06DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 逾期未決行案件管制表
     * 
     * @param payCode 給付別
     * @param endYm 截止年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectAuditRpt01DataBy(String payCode, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("endYm", endYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectAuditRpt01DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 逾期未決行案件管制表 Excel
     * 
     * @param payCode 給付別
     * @param endYm 截止年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectAuditRpt01ExcelDataBy(String payCode, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("endYm", endYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectAuditRpt01ExcelDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 老年
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1LDataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page1LDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 失能
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1KDataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page1KDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 遺屬
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1SDataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page1SDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 全給付別
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page1DataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page1DataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 老年
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2LDataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page2LDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 失能
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2KDataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page2KDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 遺屬
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2SDataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page2SDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料清單 For 受理案件統計表 全給付別
     * 
     * @param crtDate 受理日期
     * @param crtYm 受理日期年月
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectOtherRpt04Page2DataBy(String crtDate, String crtYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("crtDate", crtDate);
        map.put("crtYm", crtYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectOtherRpt04Page2DataBy", map);
    }

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次
     */
    public List<String> selectForPrintNotify(String payCode, String issuYm, String rptKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(rptKind))
            map.put("rptKind", rptKind);
        return (List<String>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectForPrintNotify", map);
    }

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次 遺屬
     */
    public List<String> selectSurvivorForPrintNotify(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return (List<String>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSurvivorForPrintNotify", map);
    }

    /**
     * 查詢 給付主檔(<code>BAAPPBASE</code>) 案件資料 FOR 核定通知書 批次 失能
     */
    public List<String> selectDisabledForPrintNotify(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return (List<String>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDisabledForPrintNotify", map);
    }

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 資料清單 老年
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthCheckLDataListBy(String payCode, String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("apNoA", apNoA);
        map.put("apNoB", apNoB);
        map.put("apNoC", apNoC);
        map.put("apNoD", apNoD);
        map.put("apNoE", apNoE);
        map.put("apNoF", apNoF);
        map.put("apNoG", apNoG);
        map.put("apNoH", apNoH);
        map.put("apNoI", apNoI);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthCheckDataListBy", map);
    }

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 資料清單 失能
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthCheckKDataListBy(String payCode, String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("apNoA", apNoA);
        map.put("apNoB", apNoB);
        map.put("apNoC", apNoC);
        map.put("apNoD", apNoD);
        map.put("apNoE", apNoE);
        map.put("apNoF", apNoF);
        map.put("apNoG", apNoG);
        map.put("apNoH", apNoH);
        map.put("apNoI", apNoI);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthCheckDataListBy", map);
    }

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 資料清單 遺屬
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectMonthCheckSDataListBy(String payCode, String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("apNo", apNo);
        map.put("apNoA", apNoA);
        map.put("apNoB", apNoB);
        map.put("apNoC", apNoC);
        map.put("apNoD", apNoD);
        map.put("apNoE", apNoE);
        map.put("apNoF", apNoF);
        map.put("apNoG", apNoG);
        map.put("apNoH", apNoH);
        map.put("apNoI", apNoI);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthCheckDataListBy", map);
    }

    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAAPPBASE</code>) 查詢每個受理案件及處理狀態
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectChkMonthDataBy(String apNo, String apNoA, String apNoB, String apNoC, String apNoD, String apNoE, String apNoF, String apNoG, String apNoH, String apNoI) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("apNoA", apNoA);
        map.put("apNoB", apNoB);
        map.put("apNoC", apNoC);
        map.put("apNoD", apNoD);
        map.put("apNoE", apNoE);
        map.put("apNoF", apNoF);
        map.put("apNoG", apNoG);
        map.put("apNoH", apNoH);
        map.put("apNoI", apNoI);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectChkMonthDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 for 給付查詢 保密資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baappbase</code> 物件的 List
     */
    public List<Baappbase> selectIdnNoDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectIdnNoDataBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 最大給付年月資料 for 眷屬資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public String selectMaxPayYmBy(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectMaxPayYmBy", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 最大給付年月資料 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Baappbase</code> 物件的List
     */
    public String selectMaxPayYmForSBy(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectMaxPayYmForSBy", map);
    }

    /**
     * 依傳入條件取得 受款人數(<code>BAAPPBASE</code>)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getPayeeCount(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getPayeeCount", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Baappbase> selectReportReplaceDataForA077(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectReportReplaceDataForA077", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Baappbase> selectReportReplaceDataForA089(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectReportReplaceDataForA089", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料是否存在
     * 
     * @param idn 身分證號
     * @return
     */
    public int selectEvtPersonDataCount(String idn) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(idn))
            map.put("idn", idn);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectEvtPersonDataCount", map);
    }

    /**
     * 依傳入條件取得受理編號
     * 
     * @param idn 身分證號
     * @return
     */
    public List<String> selectApnoDataFromMultiSource(String idn) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(idn))
            map.put("idn", idn);
        return (List<String>) getSqlMapClientTemplate().queryForList("BAAPPBASE.selectApnoDataFromMultiSource", map);
    }

    /**
     * 依傳入條件取得 地址資料
     * 
     * @param apno 受理編號
     * @return
     */
    public Baappbase selectAddrData(String apno, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apno))
            map.put("apno", apno);
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectAddrData", map);
    }

    /**
     * 依傳入條件取得 受理編號的事故者姓名
     * 
     * @param IDN 身分證號
     * @return
     */
    public String selectEvtName(String idn) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(idn))
            map.put("idn", idn);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectEvtName", map);
    }

    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) 資料 FOR 老年年金應收收回處理作業-現金收回
     * 
     * @param APNO 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectBatchPaymentReceiveDataBy(String apNo, String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectBatchCashReceiveDataBy", map);
    }

    /**
     * 更正作業報表 止付單 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param baappbase 給付主檔
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectSeqNoListForDataUpdateRpt05(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectSeqNoDataForAccRel3", map);
    }

    /**
     * 依傳入條件取得 繳款單受益者資料 (<code>BAAPPBASE</code>)
     * 
     * @param PAYMENTNO 繳款單號
     * @return <code>Baappbase</code> 物件
     */
    public Baappbase getBaappbaseData(String paymentNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(paymentNo))
            map.put("paymentNo", paymentNo);
        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectBenNameIdn", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateIssuNotifyingMkByApNo(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        getSqlMapClientTemplate().update("BAAPPBASE.updateIssuNotifyingMkByApNo", map);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param baappbase 給付主檔
     */
    public void updateUnqualifiedCauseByApNo(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        getSqlMapClientTemplate().update("BAAPPBASE.updateUnqualifiedCauseByApNo", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectMonthlyRpt05PrintData(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintData", apNo);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt05PrintCase2Data(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintCase2Data", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金 for 通知書欄位代碼 A150 不合格說明
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataA150(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintCase2DataA150", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt05PrintCase2Data1(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintCase2Data1", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataForSurvivorReport1(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintCase2DataForSurvivorReport1", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金（續發案）
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Baappbase> selectMonthlyRpt05PrintCase2DataForSurvivorReport2(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintCase2DataForSurvivorReport2", map);
    }

    /**
     * 更正作業報表 給付函核定通知書 for 給付主檔(<code>BAAPPBASE</code>) 遺屬年金
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectMonthlyRpt05PrintDataForSurvivorReport1(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectMonthlyRpt05PrintDataForSurvivorReport1", apNo);
    }
    
    /**
     * 依傳入條件取得 主檔受益人資料
     * 
     * @param apno 受理編號
     * @param idnNo 身分證號
     * @return
     */
    @DaoFieldList("APNO,IDNNO")
    public Baappbase selectPaymentBaseData(String apno,String idnNo) {
          HashMap<String, String> map = new HashMap<String, String>();
          if (StringUtils.isNotBlank(apno))
              map.put("apNo", apno);
          if (StringUtils.isNotBlank(idnNo))
              map.put("idnNo", idnNo);
         return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectPaymentBaseData", map);
    }
    
    /**
     * 查詢受益人IDN對應姓名
     * 
     * @param idn
     * @return benName
     */
    @DaoFieldList("BENNAME")
    public String selectPaymentBenName(String idn) {
         HashMap<String, String> map = new HashMap<String, String>();
         map.put("idn", idn);
         return  (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectPaymentBenName", map);
    }
        
    /**
     * 依傳入條件取得 給付主檔 (<code>BAAPPBASE</code>) FOR 遺屬受理審核清單核定通知書受文者身份證號
     * 
     * @param apNo 受理編號
     * @param name 受文者姓名
     * @return <code>BigDecimal</code> 物件
     */
    public String getSingleReceiveIdnNoBy(String apNo, String name) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("name", name);
        return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.getSingleReceiveIdnNoBy", map);
    }

    /**
     * 更新老年、失能結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param seqNo
     */
    public void updateDataForOldAgeAndDsiabledCloseStatusAlteration(String apNo, String updUser) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("updUser", updUser);

        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForOldAgeAndDsiabledCloseStatusAlteration", map);
    }
    
    /**
     * 更新遺屬結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param seqNo
     */
    public void updateDataForCloseStatusAlteration(String apNo, String seqNo, String updUser) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("updUser", updUser);

        getSqlMapClientTemplate().update("BAAPPBASE.updateDataForCloseStatusAlteration", map);
    }
    
    /**
     * 更新結案狀態 for 給付主檔(<code>BAAPPBASE</code>)
     * 
     * @param apNo
     * @param updUser
     */
    public void updateCaseTypForCloseStatusAlteration(String apNo, String updUser) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("updUser", updUser);

        getSqlMapClientTemplate().update("BAAPPBASE.updateCaseTypForCloseStatusAlteration", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 重設案件給付參數資料
     * 
     * @param apNo 受理編號
     * @return <code>Baappbase</code> 物件
     */
    @DaoFieldList("APNO")
    public Baappbase selectDataForResetPaymentParameter(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);

        return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectDataForResetPaymentParameter", map);
    }

    /**
     * 依傳入條件取得 給付主檔(<code>BAAPPBASE</code>) 資料 FOR 重設案件給付參數資料
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
    public List<Baappbase> selectDataToReGetCipbForResetPaymentParameter(String apNo) {

        return getSqlMapClientTemplate().queryForList("BAAPPBASE.selectDataToReGetCipbForResetPaymentParameter", apNo);
    }

    @DaoFieldList("KEY")
	public String selectMaxDateFromNbschool(String key) {
		return (String) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectMaxDateFromNbschool", key);
	}

    @DaoFieldList("KEY,STATUSDATE_S")
    public Baappbase selectMaxDateFromNbschool2(String key, String statusDate_S) {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("key", key);
    	map.put("statusDate_S", statusDate_S);
    	return (Baappbase) getSqlMapClientTemplate().queryForObject("BAAPPBASE.selectMaxDateFromNbschool2", map);
	}

    /**
     * 依傳入條件取得 資料 FOR 年金受理資料轉出
     * 
     * @param apNo 受理編號
     */
    @DaoFieldList("APNO")
	public List<String> ForAnnuityAcceptDataTransfer(String apNo) {
		return getSqlMapClientTemplate().queryForList("BAAPPBASEForAnnuityAcceptDataTransfer.forAnnuityAcceptDataTransfer", apNo);
	}
    
    /**
     * 依傳入條件取得 資料 FOR 老年、失能年金補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> qryAddManPayList(String apNo, String issuYm) {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("apNo", apNo);
    	map.put("issuYm", issuYm);
    	return getSqlMapClientTemplate().queryForList("BAAPPBASE.qryAddManPayList", map);
    }
    
    /**
     * 依傳入條件取得 資料 FOR 補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> qryUpdManPayList(String apNo, String issuYm) {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("apNo", apNo);
    	map.put("issuYm", issuYm);
    	return getSqlMapClientTemplate().queryForList("BAAPPBASE.qryUpdManPayList", map);
    }
    
    /**
     * 依傳入條件取得 資料 FOR 遺屬年金補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Baappbase> qryAddManPayListForSurvivor(String apNo, String issuYm) {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("apNo", apNo);
    	map.put("issuYm", issuYm);
    	return getSqlMapClientTemplate().queryForList("BAAPPBASE.qryAddManPayListForSurvivor", map);
    }

	@Override
	@DaoFieldList("APNO,SEQNO")
	public int updateCasemkByApnoAndSeqno(String apno, String seqno, String updUser) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(apno)) {
			map.put("apno", apno);
		}
		if (StringUtils.isNotBlank(seqno)) {
			map.put("seqno", seqno);
		}
		if (StringUtils.isNotBlank(updUser)) {
			map.put("updUser", updUser);
		}
		return getSqlMapClientTemplate().update("BAAPPBASE.updateCasemkByApnoAndSeqno", map);
	}
}
