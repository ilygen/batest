package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 公保年金給付核定資料
 * 
 * BBGOVNPES
 * 
 * @author 
 */
@Table("BBGOVNPES")
public class Bbgovnpes implements Serializable {

	private String id;//資料列編號
	private String jobId;//序號(UUID)
	private String dataDate;//資料日期
	private String appIdnNo;//申請人身分證號
	private String appBrDate;//申請人出生日期
	private String appName;//申請人姓名
	private String payKindG;//給付類別
	private String appDate;//申請日期
	private String updateType;//異動別
	private String payYm;//給付年月
	private String issueDate;//核付日期
	private BigDecimal appIssueAmt;//申請人核付金額
	private String evtJobDate;//事故日期
	private String evtIdnNo;//事故者身分證號
	private String evtBrDate;//事故者出生日期
	private String evtName;//事故者姓名
	private String appEvtRel;//申請人與事故者關係
	private String payReason;//領取給付原因
	private String pensDate;//年金起始日期
	private String closeDate;//結案日期
	private String govnId;//機關代碼
	private String delMk;//註銷註記
	private String crtUser;//新增者代號
	private String crtTime;//新增日期時間
	private String updUser;//異動者代號
	private String updTime;//異動日期時間
	private BigDecimal appOverAmt;//申請人超額年金金額

	
	/**
	 * 
	 */
	public Bbgovnpes() {
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
	 * @param payYm
	 * @param issueDate
	 * @param appIssueAmt
	 * @param evtJobDate
	 * @param evtIdnNo
	 * @param evtBrDate
	 * @param evtName
	 * @param appEvtRel
	 * @param payReason
	 * @param pensDate
	 * @param closeDate
	 * @param govnId
	 * @param delMk
	 * @param crtUser
	 * @param crtTime
	 * @param updUser
	 * @param updTime
	 * @param appOverAmt
	 */
	public Bbgovnpes(String id, String jobId, String dataDate, String appIdnNo, String appBrDate, String appName,
			String payKindG, String appDate, String updateType, String payYm, String issueDate, BigDecimal appIssueAmt,
			String evtJobDate, String evtIdnNo, String evtBrDate, String evtName, String appEvtRel, String payReason,
			String pensDate, String closeDate, String govnId, String delMk, String crtUser, String crtTime,
			String updUser, String updTime, BigDecimal appOverAmt) {
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
		this.payYm = payYm;
		this.issueDate = issueDate;
		this.appIssueAmt = appIssueAmt;
		this.evtJobDate = evtJobDate;
		this.evtIdnNo = evtIdnNo;
		this.evtBrDate = evtBrDate;
		this.evtName = evtName;
		this.appEvtRel = appEvtRel;
		this.payReason = payReason;
		this.pensDate = pensDate;
		this.closeDate = closeDate;
		this.govnId = govnId;
		this.delMk = delMk;
		this.crtUser = crtUser;
		this.crtTime = crtTime;
		this.updUser = updUser;
		this.updTime = updTime;
		this.appOverAmt = appOverAmt;
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
	 * @return the payYm
	 */
	public String getPayYm() {
		return payYm;
	}


	/**
	 * @param payYm the payYm to set
	 */
	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}


	/**
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}


	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
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
	 * @return the payReason
	 */
	public String getPayReason() {
		return payReason;
	}


	/**
	 * @param payReason the payReason to set
	 */
	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}


	/**
	 * @return the pensDate
	 */
	public String getPensDate() {
		return pensDate;
	}


	/**
	 * @param pensDate the pensDate to set
	 */
	public void setPensDate(String pensDate) {
		this.pensDate = pensDate;
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
	 * @return the appOverAmt
	 */
	public BigDecimal getAppOverAmt() {
		return appOverAmt;
	}


	/**
	 * @param appOverAmt the appOverAmt to set
	 */
	public void setAppOverAmt(BigDecimal appOverAmt) {
		this.appOverAmt = appOverAmt;
	}
	
	
	
	
}
