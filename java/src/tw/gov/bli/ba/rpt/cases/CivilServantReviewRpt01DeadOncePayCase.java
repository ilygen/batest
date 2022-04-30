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
public class CivilServantReviewRpt01DeadOncePayCase implements Serializable {

	private String appName;// 申請人姓名
	private String appDate;// 申請日期
	private String evtRetDate;// 死亡日期
	private String disQualMk;// 失能等級
	private String criinjdp;// 失能編號
	private String issueDate;// 核付日期
	private String appIssueAmt;// 核付金額
	private String closeDate;// 結案日期


	/**
	 * 
	 */
	public CivilServantReviewRpt01DeadOncePayCase() {
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
	 * @return the criinjdp
	 */
	public String getCriinjdp() {
		return criinjdp;
	}

	/**
	 * @param criinjdp the criinjdp to set
	 */
	public void setCriinjdp(String criinjdp) {
		this.criinjdp = criinjdp;
	}

}
