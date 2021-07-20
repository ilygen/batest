package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 眷屬資料更正 (BAMO0D073C、BAMO0D071A)
 * 
 * @author Evelyn Hsu
 */

public class DependantDataUpdateDetailForm extends BaseValidatorForm{
    
    private String method;
    
    private BigDecimal bafamilyId; // 資料列編號
    private BigDecimal baappbaseId; // 給付主檔資料編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String famEvtRel; // 眷屬與事故者關係
    private String famName; // 眷屬姓名
    private String famIdnNo; // 眷屬身分證號
    private String famBrDate; // 眷屬出生日期
    private String famAppDate; // 眷屬申請日期
    private String famDieDate; // 眷屬死亡日期
    private String famNationTyp; // 眷屬國籍別
    private String famNationCodeOption; // 眷屬國籍代碼
    private String famSex; // 眷屬性別
    private String famNationCode; // 眷屬國籍
    private String raiseChildMk; // 配偶扶養
    private String handIcapMk; // 領有重度以上身心障礙手冊或證明
    private String abanApplyMk; // 放棄請領
    private String abanApsYm; // 放棄請領起始年
    private String interDictMk; // 受禁治產宣告註記
    private String interDictDate; // 受禁治產宣告日期
    private String repealInterDictDate;//禁治產撤消日期
    private String relatChgDate; // 親屬關係變動日期
    private String adoPtDate; // 收養日期
    private String benMissingSdate; // 受益人失蹤期間(起)
    private String benMissingEdate; // 受益人失蹤期間(迄)
    private String prisonSdate; // 監管期間(起)
    private String prisonEdate; // 監管期間(迄)
    private String marryDate; // 結婚日期
    private String divorceDate; // 離婚日期
    private String studMk; // 在學
    private String studSdate; // 在學起始年月
    private String studEdate; // 在學結束年月
    private String monIncomeMk; // 每月工作收入註記
    private String monIncome; // 每月工作收入
    private String closeDate; // 結案日期
    private String closeCause; // 結案原因
    private String idnChkYm;//身分查核年月
    private String idnChkNote;//身分查核年月註記
    private String idnChkY;//身分查核年
    private String idnChkM;//身分查核月
    private String compelMk; // 強迫不合格註記
    
    private String oldFamEvtRel; // 眷屬與事故者關係
    private String oldFamName; // 眷屬姓名
    private String oldFamIdnNo; // 眷屬身分證號
    private String oldFamBrDate; // 眷屬出生日期
    private String oldFamAppDate; // 眷屬申請日期
    private String oldFamDieDate; // 眷屬死亡日期
    private String oldFamNationTyp; // 眷屬國籍別
    private String oldFamNationCodeOption; // 眷屬國籍代碼
    private String oldFamSex; // 眷屬性別
    private String oldFamNationCode; // 眷屬國籍
    private String oldRaiseChildMk; // 配偶扶養
    private String oldHandIcapMk; // 領有重度以上身心障礙手冊或證明
    private String oldAbanApplyMk; // 放棄請領
    private String oldAbanApsYm; // 放棄請領起始年
    private String oldInterDictMk; // 受禁治產宣告註記
    private String oldInterDictDate; // 受禁治產宣告日期
    private String oldRepealInterDictDate;//禁治產撤消日期
    private String oldRelatChgDate; // 親屬關係變動日期
    private String oldAdoPtDate; // 收養日期
    private String oldBenMissingSdate; // 受益人失蹤期間(起)
    private String oldBenMissingEdate; // 受益人失蹤期間(迄)
    private String oldPrisonSdate; // 監管期間(起)
    private String oldPrisonEdate; // 監管期間(迄)
    private String oldMarryDate; // 結婚日期
    private String oldDivorceDate; // 離婚日期
    private String oldStudMk; // 在學
    private String oldStudSdate; // 在學起始年月
    private String oldStudEdate; // 在學結束年月
    private String oldMonIncomeMk; // 每月工作收入註記
    private String oldMonIncome; // 每月工作收入
    private String oldCloseDate; // 結案日期
    private String oldCloseCause; // 結案原因
    private String oldIdnChkYm;//身分查核年月
    private String oldIdnChkNote;//身分查核年月註記
    private String oldIdnChkY;//身分查核年
    private String oldIdnChkM;//身分查核月
    private String oldCompelMk; // 強迫不合格註記
    private String ableApsYm; //得請領起月
    private String idnoExist;//
    
    private String schoolCode; //學校代碼
    private String schoolCodeOption; //學校代碼 下單選單

    public void reset(ActionMapping mapping, HttpServletRequest request){
        monIncomeMk="";
        abanApplyMk="";
        studMk="";
        raiseChildMk="";
        handIcapMk="";
        abanApsYm="";
        monIncome="";
        interDictMk="";
        compelMk = "";
    }
    
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }
    
    

    public BigDecimal getBafamilyId() {
        return bafamilyId;
    }

    public void setBafamilyId(BigDecimal bafamilyId) {
        this.bafamilyId = bafamilyId;
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

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamDieDate() {
        return famDieDate;
    }

    public void setFamDieDate(String famDieDate) {
        this.famDieDate = famDieDate;
    }

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
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

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
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

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public String getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(String monIncome) {
        this.monIncome = monIncome;
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

    public String getFamNationCodeOption() {
        return famNationCodeOption;
    }

    public void setFamNationCodeOption(String famNationCodeOption) {
        this.famNationCodeOption = famNationCodeOption;
    }

    public String getIdnChkNote() {
        return idnChkNote;
    }

    public void setIdnChkNote(String idnChkNote) {
        this.idnChkNote = idnChkNote;
    }

    public String getIdnChkY() {
        return idnChkY;
    }

    public void setIdnChkY(String idnChkY) {
        this.idnChkY = idnChkY;
    }

    public String getIdnChkM() {
        return idnChkM;
    }

    public void setIdnChkM(String idnChkM) {
        this.idnChkM = idnChkM;
    }

    public String getRepealInterDictDate() {
        return repealInterDictDate;
    }

    public void setRepealInterDictDate(String repealInterDictDate) {
        this.repealInterDictDate = repealInterDictDate;
    }

    public String getOldFamEvtRel() {
        return oldFamEvtRel;
    }

    public void setOldFamEvtRel(String oldFamEvtRel) {
        this.oldFamEvtRel = oldFamEvtRel;
    }

    public String getOldFamName() {
        return oldFamName;
    }

    public void setOldFamName(String oldFamName) {
        this.oldFamName = oldFamName;
    }

    public String getOldFamIdnNo() {
        return oldFamIdnNo;
    }

    public void setOldFamIdnNo(String oldFamIdnNo) {
        this.oldFamIdnNo = oldFamIdnNo;
    }

    public String getOldFamBrDate() {
        return oldFamBrDate;
    }

    public void setOldFamBrDate(String oldFamBrDate) {
        this.oldFamBrDate = oldFamBrDate;
    }

    public String getOldFamAppDate() {
        return oldFamAppDate;
    }

    public void setOldFamAppDate(String oldFamAppDate) {
        this.oldFamAppDate = oldFamAppDate;
    }

    public String getOldFamDieDate() {
        return oldFamDieDate;
    }

    public void setOldFamDieDate(String oldFamDieDate) {
        this.oldFamDieDate = oldFamDieDate;
    }

    public String getOldFamNationTyp() {
        return oldFamNationTyp;
    }

    public void setOldFamNationTyp(String oldFamNationTyp) {
        this.oldFamNationTyp = oldFamNationTyp;
    }

    public String getOldFamNationCodeOption() {
        return oldFamNationCodeOption;
    }

    public void setOldFamNationCodeOption(String oldFamNationCodeOption) {
        this.oldFamNationCodeOption = oldFamNationCodeOption;
    }

    public String getOldFamSex() {
        return oldFamSex;
    }

    public void setOldFamSex(String oldFamSex) {
        this.oldFamSex = oldFamSex;
    }

    public String getOldFamNationCode() {
        return oldFamNationCode;
    }

    public void setOldFamNationCode(String oldFamNationCode) {
        this.oldFamNationCode = oldFamNationCode;
    }

    public String getOldRaiseChildMk() {
        return oldRaiseChildMk;
    }

    public void setOldRaiseChildMk(String oldRaiseChildMk) {
        this.oldRaiseChildMk = oldRaiseChildMk;
    }

    public String getOldHandIcapMk() {
        return oldHandIcapMk;
    }

    public void setOldHandIcapMk(String oldHandIcapMk) {
        this.oldHandIcapMk = oldHandIcapMk;
    }

    public String getOldAbanApplyMk() {
        return oldAbanApplyMk;
    }

    public void setOldAbanApplyMk(String oldAbanApplyMk) {
        this.oldAbanApplyMk = oldAbanApplyMk;
    }

    public String getOldAbanApsYm() {
        return oldAbanApsYm;
    }

    public void setOldAbanApsYm(String oldAbanApsYm) {
        this.oldAbanApsYm = oldAbanApsYm;
    }

    public String getOldInterDictMk() {
        return oldInterDictMk;
    }

    public void setOldInterDictMk(String oldInterDictMk) {
        this.oldInterDictMk = oldInterDictMk;
    }

    public String getOldInterDictDate() {
        return oldInterDictDate;
    }

    public void setOldInterDictDate(String oldInterDictDate) {
        this.oldInterDictDate = oldInterDictDate;
    }

    public String getOldRepealInterDictDate() {
        return oldRepealInterDictDate;
    }

    public void setOldRepealInterDictDate(String oldRepealInterDictDate) {
        this.oldRepealInterDictDate = oldRepealInterDictDate;
    }

    public String getOldRelatChgDate() {
        return oldRelatChgDate;
    }

    public void setOldRelatChgDate(String oldRelatChgDate) {
        this.oldRelatChgDate = oldRelatChgDate;
    }

    public String getOldAdoPtDate() {
        return oldAdoPtDate;
    }

    public void setOldAdoPtDate(String oldAdoPtDate) {
        this.oldAdoPtDate = oldAdoPtDate;
    }

    public String getOldBenMissingSdate() {
        return oldBenMissingSdate;
    }

    public void setOldBenMissingSdate(String oldBenMissingSdate) {
        this.oldBenMissingSdate = oldBenMissingSdate;
    }

    public String getOldBenMissingEdate() {
        return oldBenMissingEdate;
    }

    public void setOldBenMissingEdate(String oldBenMissingEdate) {
        this.oldBenMissingEdate = oldBenMissingEdate;
    }

    public String getOldPrisonSdate() {
        return oldPrisonSdate;
    }

    public void setOldPrisonSdate(String oldPrisonSdate) {
        this.oldPrisonSdate = oldPrisonSdate;
    }

    public String getOldPrisonEdate() {
        return oldPrisonEdate;
    }

    public void setOldPrisonEdate(String oldPrisonEdate) {
        this.oldPrisonEdate = oldPrisonEdate;
    }

    public String getOldMarryDate() {
        return oldMarryDate;
    }

    public void setOldMarryDate(String oldMarryDate) {
        this.oldMarryDate = oldMarryDate;
    }

    public String getOldDivorceDate() {
        return oldDivorceDate;
    }

    public void setOldDivorceDate(String oldDivorceDate) {
        this.oldDivorceDate = oldDivorceDate;
    }

    public String getOldStudMk() {
        return oldStudMk;
    }

    public void setOldStudMk(String oldStudMk) {
        this.oldStudMk = oldStudMk;
    }

    public String getOldStudSdate() {
        return oldStudSdate;
    }

    public void setOldStudSdate(String oldStudSdate) {
        this.oldStudSdate = oldStudSdate;
    }

    public String getOldStudEdate() {
        return oldStudEdate;
    }

    public void setOldStudEdate(String oldStudEdate) {
        this.oldStudEdate = oldStudEdate;
    }

    public String getOldMonIncomeMk() {
        return oldMonIncomeMk;
    }

    public void setOldMonIncomeMk(String oldMonIncomeMk) {
        this.oldMonIncomeMk = oldMonIncomeMk;
    }

    public String getOldMonIncome() {
        return oldMonIncome;
    }

    public void setOldMonIncome(String oldMonIncome) {
        this.oldMonIncome = oldMonIncome;
    }

    public String getOldCloseDate() {
        return oldCloseDate;
    }

    public void setOldCloseDate(String oldCloseDate) {
        this.oldCloseDate = oldCloseDate;
    }

    public String getOldCloseCause() {
        return oldCloseCause;
    }

    public void setOldCloseCause(String oldCloseCause) {
        this.oldCloseCause = oldCloseCause;
    }

    public String getOldIdnChkNote() {
        return oldIdnChkNote;
    }

    public void setOldIdnChkNote(String oldIdnChkNote) {
        this.oldIdnChkNote = oldIdnChkNote;
    }

    public String getOldIdnChkY() {
        return oldIdnChkY;
    }

    public void setOldIdnChkY(String oldIdnChkY) {
        this.oldIdnChkY = oldIdnChkY;
    }

    public String getOldIdnChkM() {
        return oldIdnChkM;
    }

    public void setOldIdnChkM(String oldIdnChkM) {
        this.oldIdnChkM = oldIdnChkM;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public String getOldIdnChkYm() {
        return oldIdnChkYm;
    }

    public void setOldIdnChkYm(String oldIdnChkYm) {
        this.oldIdnChkYm = oldIdnChkYm;
    }

	public String getIdnoExist() {
		return idnoExist;
	}

	public void setIdnoExist(String idnoExist) {
		this.idnoExist = idnoExist;
	}
	
    public String getCompelMk() {
        return compelMk;
    }

    public void setCompelMk(String compelMk) {
        this.compelMk = compelMk;
    }

    public String getOldCompelMk() {
        return oldCompelMk;
    }

    public void setOldCompelMk(String oldCompelMk) {
        this.oldCompelMk = oldCompelMk;
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

	public String getSchoolCodeOption() {
		return schoolCodeOption;
	}

	public void setSchoolCodeOption(String schoolCodeOption) {
		this.schoolCodeOption = schoolCodeOption;
	}


    
    
}
