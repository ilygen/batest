/**
 * 
 */
package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;

/**
 * 
 * @author kn0561
 *
 */
public class SoldierReviewRpt01DeadPayCase implements Serializable {
	
	private String appName;// 申請人姓名
	private String appDate;// 申請日期
	private String evtRetDate;// 致身心障礙日
	private String disQualMk;// 身障等級
	private String disEvtCode;// 身障編號
	private String appIssueAmt;// 核付金額
	private String appIssueDate;// 核付日期
	private String closeDate;// 結案日期

	
	/**
	 * 
	 */
	public SoldierReviewRpt01DeadPayCase() {
		// TODO Auto-generated constructor stub
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
	 * @return the appIssueAmt
	 */
	public String getAppIssueAmt() {
		return appIssueAmt;
	}

	/**
	 * @param appIssueAmt the appIssueAmt to set
	 */
	public void setAppIssueAmt(String appIssueAmt) {
		this.appIssueAmt = appIssueAmt;
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

}
