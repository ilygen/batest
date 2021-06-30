package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 眷屬檔 (<code>BAFAMILY</code>)
 * 
 * @author Evelyn Hsu
 */
@Table("BAFAMILY")
public class Bafamily implements Serializable {

    @PkeyField("BAFAMILYID")
    private BigDecimal bafamilyId; // 資料編號

    @LogField("BAAPPBASEID")
    private BigDecimal baappbaseId;// 給付主檔資料編號

    @LogField("APNO")
    private String apNo;// 受理編號

    @LogField("SEQNO")
    private String seqNo;// 序號

    @LogField("FAMAPPDATE")
    private String famAppDate;// 眷屬申請日期

    @LogField("FAMIDNNO")
    private String famIdnNo;// 眷屬身分證號

    @LogField("FAMNAME")
    private String famName;// 眷屬姓名

    @LogField("FAMBRDATE")
    private String famBrDate;// 眷屬出生日期

    @LogField("FAMSEX")
    private String famSex;// 眷屬性別

    @LogField("FAMNATIONTYP")
    private String famNationTyp;// 眷屬國籍別

    @LogField("FAMNATIONCODE")
    private String famNationCode;// 眷屬國籍

    @LogField("FAMEVTREL")
    private String famEvtRel;// 眷屬與事故者關係

    @LogField("FAMDIEDATE")
    private String famDieDate;// 眷屬死亡日期

    @LogField("RELATCHGDATE")
    private String relatChgDate;// 親屬關係變動日期

    @LogField("RAISECHILDMK")
    private String raiseChildMk;// 配偶扶養

    @LogField("HANDICAPMK")
    private String handIcapMk;// 領有重度以上身心障礙手冊或證明

    @LogField("MONINCOMEMK")
    private String monIncomeMk;// 每月工作收入註記

    @LogField("MONINCOME")
    private BigDecimal monIncome;// 每月工作收入

    @LogField("MARRYDATE")
    private String marryDate;// 結婚日期

    @LogField("DIVORCEDATE")
    private String divorceDate;// 離婚日期

    @LogField("ABANAPPLYMK")
    private String abanApplyMk;// 放棄請領

    @LogField("ABANAPSYM")
    private String abanApsYm;// 放棄請領起始年月

    @LogField("INTERDICTMK")
    private String interDictMk;// 受禁治產(監護)宣告
    
    @LogField("INTERDICTDATE")
    private String interDictDate;// 受禁治產(監護)宣告日期
    
    @LogField("REPEALINTERDICTDATE")
    private String repealInterDictDate;//禁治產撤消日期

    @LogField("ADOPTDATE")
    private String adoPtDate;// 收養日期

    @LogField("BENMISSINGSDATE")
    private String benMissingSdate;// 受益人失蹤期間(起)

    @LogField("BENMISSINGEDATE")
    private String benMissingEdate;// 受益人失蹤期間(迄)

    @LogField("PRISONSDATE")
    private String prisonSdate;// 監管期間(起)

    @LogField("PRISONEDATE")
    private String prisonEdate;// 監管期間(迄)

    @LogField("STUDMK")
    private String studMk;// 在學

    @LogField("STUDSDATE")
    private String studSdate;// 在學起始年月

    @LogField("STUDEDATE")
    private String studEdate;// 在學結束年月

    @LogField("CLOSEDATE")
    private String closeDate;// 結案日期

    @LogField("CLOSECAUSE")
    private String closeCause;// 結案原因

    @LogField("DELMK")
    private String delMk;// 刪除註記

    @LogField("CRTUSER")
    private String crtUser;// 新增者代號

    @LogField("CRTTIME")
    private String crtTime;// 新增日期時間

    @LogField("UPDUSER")
    private String updUser;// 異動者代號

    @LogField("UPDTIME")
    private String updTime;// 異動日期時間
    
    @LogField("IDNCHKYM")
    private String idnChkYm; //身份查核年月
    
    @LogField("IDNCHKNOTE")
    private String idnChkNote; //身分查核年月註記

    @LogField("COMPELMK")
    private String compelMk; //強迫不合格註記
    
    @LogField("ABLEAPSYM")
    private String ableApsYm; //得請領起月
    
    @LogField("SCHOOLCODE")
    private String schoolCode; //學校代碼
    
    // Field not in BAFAMILY
    // [
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtIdnNo;// 事故者身分證號
    private String appDate;// 申請日期
    private String famNationName;// 眷屬國籍名稱
    private String evtDieDate; //事故者死亡日期
    private String evtJobDate; //事故日期

    // ]

    public String getFamNationName() {
        return famNationName;
    }

    public void setFamNationName(String famNationName) {
        this.famNationName = famNationName;
    }

    public BigDecimal getBafamilyId() {
        return bafamilyId;
    }

    public void setBafamilyId(BigDecimal bafamilyId) {
        this.bafamilyId = bafamilyId;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public String getFamDieDate() {
        return famDieDate;
    }

    public void setFamDieDate(String famDieDate) {
        this.famDieDate = famDieDate;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public BigDecimal getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(BigDecimal monIncome) {
        this.monIncome = monIncome;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getDivorceDate() {
        return divorceDate;
    }

    public void setDivorceDate(String divorceDate) {
        this.divorceDate = divorceDate;
    }

    public String getAbanApplyMk() {
        return abanApplyMk;
    }

    public void setAbanApplyMk(String abanApplyMk) {
        this.abanApplyMk = abanApplyMk;
    }

    public String getAbanApsYm() {
        return abanApsYm;
    }

    public void setAbanApsYm(String abanApsYm) {
        this.abanApsYm = abanApsYm;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public String getInterDictDate() {
        return interDictDate;
    }

    public void setInterDictDate(String interDictDate) {
        this.interDictDate = interDictDate;
    }

    public String getAdoPtDate() {
        return adoPtDate;
    }

    public void setAdoPtDate(String adoPtDate) {
        this.adoPtDate = adoPtDate;
    }

    public String getBenMissingSdate() {
        return benMissingSdate;
    }

    public void setBenMissingSdate(String benMissingSdate) {
        this.benMissingSdate = benMissingSdate;
    }

    public String getBenMissingEdate() {
        return benMissingEdate;
    }

    public void setBenMissingEdate(String benMissingEdate) {
        this.benMissingEdate = benMissingEdate;
    }

    public String getPrisonSdate() {
        return prisonSdate;
    }

    public void setPrisonSdate(String prisonSdate) {
        this.prisonSdate = prisonSdate;
    }

    public String getPrisonEdate() {
        return prisonEdate;
    }

    public void setPrisonEdate(String prisonEdate) {
        this.prisonEdate = prisonEdate;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getStudSdate() {
        return studSdate;
    }

    public void setStudSdate(String studSdate) {
        this.studSdate = studSdate;
    }

    public String getStudEdate() {
        return studEdate;
    }

    public void setStudEdate(String studEdate) {
        this.studEdate = studEdate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getDelMk() {
        return delMk;
    }

    public void setDelMk(String delMk) {
        this.delMk = delMk;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getIdnChkNote() {
        return idnChkNote;
    }

    public void setIdnChkNote(String idnChkNote) {
        this.idnChkNote = idnChkNote;
    }

    public String getRepealInterDictDate() {
        return repealInterDictDate;
    }

    public void setRepealInterDictDate(String repealInterDictDate) {
        this.repealInterDictDate = repealInterDictDate;
    }
    
    public String getCompelMk() {
        return compelMk;
    }

    public void setCompelMk(String compelMk) {
        this.compelMk = compelMk;
    }

	public String getAbleApsYm() {
		return ableApsYm;
	}

	public void setAbleApsYm(String ableApsYm) {
		this.ableApsYm = ableApsYm;
	}

	public String getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}    

    
}
