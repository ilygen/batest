package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 軍保核定資料檔
 * 
 * BBMTYBEF
 * 
 * @author 
 */
@Table("BBMTYBEF")
public class Bbmtybef implements Serializable {

	private String id;//序號
	private String jobId;//排程ID
	private String dataDate;//資料日期
	private String appIdnNo;//申請人身分證號
	private String appBrDate;//申請人出生日期
	private String appName;//申請人姓名
	private String payKindG;//給付類別
	private String appDate;//申請日期
	private String updateType;//異動別
	private String appIssueDate;//申請人核付日期(或繳回日期)
	private BigDecimal appIssueAmt;//申請人核付金額(或繳回金額)
	private String evtJobDate;//事故日期
	private String evtIdnNo;//事故者身分證號
	private String evtBrDate;//事故者出生日期
	private String evtName;//事故者姓名
	private BigDecimal issueAmt;//同事故者全案核付金額
	private BigDecimal issueAppNo;//同事故者全案請領人數
	private String appEvtRel;//申請人與事故者關係
	private String disQualMk;//身心障礙等級
	private String disEvtCode;//殘廢事故種類
	private String closeDate;//結案日期(不給付日期)
	private String evtRetDate;//退伍生效日期(軍保退保日期)
	private String govnId;//機關代碼
	private String disReason;//身心障礙原因說明
	private String crtUser;//新增者代號
	private String crtTime;//新增日期時間
	private String updUser;//異動者代號
	private String updTime;//異動日期時間
	private String delMk;//註銷
	/**
	 * 
	 */
	public Bbmtybef() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param jobId
	 * @param dataDate
	 * @param appIdnNo
	 * @param appBrDate
	 * @param appName
	 * @param payKindG
	 * @param appDate
	 * @param updateType
	 * @param appIssueDate
	 * @param appIssueAmt
	 * @param evtJobDate
	 * @param evtIdnNo
	 * @param evtBrDate
	 * @param evtName
	 * @param issueAmt
	 * @param issueAppNo
	 * @param appEvtRel
	 * @param disQualMk
	 * @param disEvtCode
	 * @param closeDate
	 * @param evtRetDate
	 * @param govnId
	 * @param disReason
	 * @param crtUser
	 * @param crtTime
	 * @param updUser
	 * @param updTime
	 * @param delMk
	 */
	public Bbmtybef(String id, String jobId, String dataDate, String appIdnNo, String appBrDate, String appName,
			String payKindG, String appDate, String updateType, String appIssueDate, BigDecimal appIssueAmt,
			String evtJobDate, String evtIdnNo, String evtBrDate, String evtName, BigDecimal issueAmt,
			BigDecimal issueAppNo, String appEvtRel, String disQualMk, String disEvtCode, String closeDate,
			String evtRetDate, String govnId, String disReason, String crtUser, String crtTime, String updUser,
			String updTime, String delMk) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.dataDate = dataDate;
		this.appIdnNo = appIdnNo;
		this.appBrDate = appBrDate;
		this.appName = appName;
		this.payKindG = payKindG;
		this.appDate = appDate;
		this.updateType = updateType;
		this.appIssueDate = appIssueDate;
		this.appIssueAmt = appIssueAmt;
		this.evtJobDate = evtJobDate;
		this.evtIdnNo = evtIdnNo;
		this.evtBrDate = evtBrDate;
		this.evtName = evtName;
		this.issueAmt = issueAmt;
		this.issueAppNo = issueAppNo;
		this.appEvtRel = appEvtRel;
		this.disQualMk = disQualMk;
		this.disEvtCode = disEvtCode;
		this.closeDate = closeDate;
		this.evtRetDate = evtRetDate;
		this.govnId = govnId;
		this.disReason = disReason;
		this.crtUser = crtUser;
		this.crtTime = crtTime;
		this.updUser = updUser;
		this.updTime = updTime;
		this.delMk = delMk;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the dataDate
	 */
	public String getDataDate() {
		return dataDate;
	}
	/**
	 * @param dataDate the dataDate to set
	 */
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	/**
	 * @return the appIdnNo
	 */
	public String getAppIdnNo() {
		return appIdnNo;
	}
	/**
	 * @param appIdnNo the appIdnNo to set
	 */
	public void setAppIdnNo(String appIdnNo) {
		this.appIdnNo = appIdnNo;
	}
	/**
	 * @return the appBrDate
	 */
	public String getAppBrDate() {
		return appBrDate;
	}
	/**
	 * @param appBrDate the appBrDate to set
	 */
	public void setAppBrDate(String appBrDate) {
		this.appBrDate = appBrDate;
	}
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the payKindG
	 */
	public String getPayKindG() {
		return payKindG;
	}
	/**
	 * @param payKindG the payKindG to set
	 */
	public void setPayKindG(String payKindG) {
		this.payKindG = payKindG;
	}
	/**
	 * @return the appDate
	 */
	public String getAppDate() {
		return appDate;
	}
	/**
	 * @param appDate the appDate to set
	 */
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	/**
	 * @return the updateType
	 */
	public String getUpdateType() {
		return updateType;
	}
	/**
	 * @param updateType the updateType to set
	 */
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	/**
	 * @return the appIssueDate
	 */
	public String getAppIssueDate() {
		return appIssueDate;
	}
	/**
	 * @param appIssueDate the appIssueDate to set
	 */
	public void setAppIssueDate(String appIssueDate) {
		this.appIssueDate = appIssueDate;
	}
	/**
	 * @return the appIssueAmt
	 */
	public BigDecimal getAppIssueAmt() {
		return appIssueAmt;
	}
	/**
	 * @param appIssueAmt the appIssueAmt to set
	 */
	public void setAppIssueAmt(BigDecimal appIssueAmt) {
		this.appIssueAmt = appIssueAmt;
	}
	/**
	 * @return the evtJobDate
	 */
	public String getEvtJobDate() {
		return evtJobDate;
	}
	/**
	 * @param evtJobDate the evtJobDate to set
	 */
	public void setEvtJobDate(String evtJobDate) {
		this.evtJobDate = evtJobDate;
	}
	/**
	 * @return the evtIdnNo
	 */
	public String getEvtIdnNo() {
		return evtIdnNo;
	}
	/**
	 * @param evtIdnNo the evtIdnNo to set
	 */
	public void setEvtIdnNo(String evtIdnNo) {
		this.evtIdnNo = evtIdnNo;
	}
	/**
	 * @return the evtBrDate
	 */
	public String getEvtBrDate() {
		return evtBrDate;
	}
	/**
	 * @param evtBrDate the evtBrDate to set
	 */
	public void setEvtBrDate(String evtBrDate) {
		this.evtBrDate = evtBrDate;
	}
	/**
	 * @return the evtName
	 */
	public String getEvtName() {
		return evtName;
	}
	/**
	 * @param evtName the evtName to set
	 */
	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}
	/**
	 * @return the issueAmt
	 */
	public BigDecimal getIssueAmt() {
		return issueAmt;
	}
	/**
	 * @param issueAmt the issueAmt to set
	 */
	public void setIssueAmt(BigDecimal issueAmt) {
		this.issueAmt = issueAmt;
	}
	/**
	 * @return the issueAppNo
	 */
	public BigDecimal getIssueAppNo() {
		return issueAppNo;
	}
	/**
	 * @param issueAppNo the issueAppNo to set
	 */
	public void setIssueAppNo(BigDecimal issueAppNo) {
		this.issueAppNo = issueAppNo;
	}
	/**
	 * @return the appEvtRel
	 */
	public String getAppEvtRel() {
		return appEvtRel;
	}
	/**
	 * @param appEvtRel the appEvtRel to set
	 */
	public void setAppEvtRel(String appEvtRel) {
		this.appEvtRel = appEvtRel;
	}
	/**
	 * @return the disQualMk
	 */
	public String getDisQualMk() {
		return disQualMk;
	}
	/**
	 * @param disQualMk the disQualMk to set
	 */
	public void setDisQualMk(String disQualMk) {
		this.disQualMk = disQualMk;
	}
	/**
	 * @return the disEvtCode
	 */
	public String getDisEvtCode() {
		return disEvtCode;
	}
	/**
	 * @param disEvtCode the disEvtCode to set
	 */
	public void setDisEvtCode(String disEvtCode) {
		this.disEvtCode = disEvtCode;
	}
	/**
	 * @return the closeDate
	 */
	public String getCloseDate() {
		return closeDate;
	}
	/**
	 * @param closeDate the closeDate to set
	 */
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	/**
	 * @return the evtRetDate
	 */
	public String getEvtRetDate() {
		return evtRetDate;
	}
	/**
	 * @param evtRetDate the evtRetDate to set
	 */
	public void setEvtRetDate(String evtRetDate) {
		this.evtRetDate = evtRetDate;
	}
	/**
	 * @return the govnId
	 */
	public String getGovnId() {
		return govnId;
	}
	/**
	 * @param govnId the govnId to set
	 */
	public void setGovnId(String govnId) {
		this.govnId = govnId;
	}
	/**
	 * @return the disReason
	 */
	public String getDisReason() {
		return disReason;
	}
	/**
	 * @param disReason the disReason to set
	 */
	public void setDisReason(String disReason) {
		this.disReason = disReason;
	}
	/**
	 * @return the crtUser
	 */
	public String getCrtUser() {
		return crtUser;
	}
	/**
	 * @param crtUser the crtUser to set
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}
	/**
	 * @return the crtTime
	 */
	public String getCrtTime() {
		return crtTime;
	}
	/**
	 * @param crtTime the crtTime to set
	 */
	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}
	/**
	 * @return the updUser
	 */
	public String getUpdUser() {
		return updUser;
	}
	/**
	 * @param updUser the updUser to set
	 */
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	/**
	 * @return the updTime
	 */
	public String getUpdTime() {
		return updTime;
	}
	/**
	 * @param updTime the updTime to set
	 */
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	/**
	 * @return the delMk
	 */
	public String getDelMk() {
		return delMk;
	}
	/**
	 * @param delMk the delMk to set
	 */
	public void setDelMk(String delMk) {
		this.delMk = delMk;
	}
	
	
	
}
