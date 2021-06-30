package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 勞保年金線上作業檔 (<code>BAONLINEJOB</code>)
 *
 * @author Noctis
 */
public class Baonlinejob implements Serializable {
	private BigDecimal baonlinejobId; //資料列編號
	private String apNo; //受理編號
	private String evtName; //事故者姓名
	private String evtIdnNo; //事故者身分證號
	private String issuYm; //核定年月
	private String chkDate; //核定日期
	private String adjIssuYmMk; //調整核定年月註記 
	private String caseTyp; //案件類別
	private String procStat; //處理狀態
	private String manchkMk; //人工審核結果
	private String procType; //處理類型
	private String status; //處理狀態
	
	private String procTime;//系統日期時間
	private String procEmpNo;//執行作業人員員工編號
	private String procDepId;//執行作業人員單位代碼
	private String procIp;//執行作業人員IP 
	private String caseMk;//案件註記
	
	
	public BigDecimal getBaonlinejobId() {
		return baonlinejobId;
	}
	public void setBaonlinejobId(BigDecimal baonlinejobId) {
		this.baonlinejobId = baonlinejobId;
	}
	public String getApNo() {
		return apNo;
	}
	public void setApNo(String apNo) {
		this.apNo = apNo;
	}
	public String getEvtName() {
		return evtName;
	}
	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}
	public String getEvtIdnNo() {
		return evtIdnNo;
	}
	public void setEvtIdnNo(String evtIdnNo) {
		this.evtIdnNo = evtIdnNo;
	}
	public String getIssuYm() {
		return issuYm;
	}
	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}
	public String getChkDate() {
		return chkDate;
	}
	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}
	public String getAdjIssuYmMk() {
		return adjIssuYmMk;
	}
	public void setAdjIssuYmMk(String adjIssuYmMk) {
		this.adjIssuYmMk = adjIssuYmMk;
	}
	public String getCaseTyp() {
		return caseTyp;
	}
	public void setCaseTyp(String caseTyp) {
		this.caseTyp = caseTyp;
	}
	public String getProcStat() {
		return procStat;
	}
	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}
	public String getManchkMk() {
		return manchkMk;
	}
	public void setManchkMk(String manchkMk) {
		this.manchkMk = manchkMk;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcTime() {
		return procTime;
	}
	public void setProcTime(String procTime) {
		this.procTime = procTime;
	}
	public String getProcEmpNo() {
		return procEmpNo;
	}
	public void setProcEmpNo(String procEmpNo) {
		this.procEmpNo = procEmpNo;
	}
	public String getProcDepId() {
		return procDepId;
	}
	public void setProcDepId(String procDepId) {
		this.procDepId = procDepId;
	}
	public String getProcIp() {
		return procIp;
	}
	public void setProcIp(String procIp) {
		this.procIp = procIp;
	}
	public String getCaseMk() {
		return caseMk;
	}
	public void setCaseMk(String caseMk) {
		this.caseMk = caseMk;
	}

}
