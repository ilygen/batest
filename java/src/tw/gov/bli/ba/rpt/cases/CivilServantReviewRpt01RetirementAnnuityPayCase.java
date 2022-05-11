/**
 * 
 */
package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 *  
 * @author kn0561
 *
 */
public class CivilServantReviewRpt01RetirementAnnuityPayCase implements Serializable {

	private String appName;// 申請人姓名
	private String appDate;// 申請日期
	private String evtJobDate;// 退休日期
	private String pensDate;// 年金起始日
	private String payYm;// 給付年月
	private String issueDate;// 核付日期
	private String appIssueAmt;// 核付金額
	private String closeDate;// 結案日期

	/**
	 * 
	 */
	public CivilServantReviewRpt01RetirementAnnuityPayCase() {
		// TODO Auto-generated constructor stub
	}

    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
        else
            return StringUtils.defaultString(payYm);
    }
	
    public String getIssueDateString() {
        if (StringUtils.length(issueDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(issueDate), false);
        else
            return StringUtils.defaultString(issueDate);
    }

    public String getAppDateString() {
        if (StringUtils.length(appDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(appDate), false);
        else
            return StringUtils.defaultString(appDate);
    }

    public String getEvtJobDateString() {
        if (StringUtils.length(evtJobDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtJobDate), false);
        else
            return StringUtils.defaultString(evtJobDate);
    }

    public String getPensDateString() {
        if (StringUtils.length(pensDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(pensDate), false);
        else
            return StringUtils.defaultString(pensDate);
    }

    public String getCloseDateString() {
        if (StringUtils.length(closeDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(closeDate), false);
        else
            return StringUtils.defaultString(closeDate);
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

}
