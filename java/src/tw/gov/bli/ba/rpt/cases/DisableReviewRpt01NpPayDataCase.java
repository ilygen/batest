package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單 - 請領他類給付資料 - 申請國保給付記錄
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01NpPayDataCase implements Serializable{

 // 國保給付主檔 (NBAPPBASE)
    // [
    private String appDate; // 申請日期(收件日期)
    private String apNo; // 受理編號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String payYms;
    private String payYme;
    private String evteeName; // 事故者姓名
    private String evtDt; // 事故日期
    private BigDecimal issueAmt; // 核定金額
    private String manChkFlg; // 人工審核結果
    private String acceptMark; // 合格註記
    private String closeReason; //結案原因
    private String closeDt; //結案日期
    private String handIcApDt; //手冊鑑定日期
    // ]

    // 國保給付核定檔 (NBDAPR)
    // [
    private String chkDt; // 核定日期
    private String aplPayDate; // 帳務日期
    // ]

    /**
     * 申請日期(收件日期)
     * 
     * @return
     */
    public String getAppDateString() {
        if (StringUtils.length(appDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(appDate), false);
        else
            return StringUtils.defaultString(appDate);
    }

    /**
     * 核定年月
     * 
     * @return
     */
    public String getIssuYmString() {
        if (StringUtils.length(issuYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), false);
        else
            return StringUtils.defaultString(issuYm);
    }

    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
        else
            return StringUtils.defaultString(payYm);
    }

    /**
     * 事故日期
     * 
     * @return
     */
    public String getEvtDtString() {
        if (StringUtils.length(evtDt) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtDt), false);
        else
            return StringUtils.defaultString(evtDt);
    }
    
    /**
     * 結案日期
     * 
     * @return
     */
    public String getCloseDtString() {
        if (StringUtils.length(closeDt) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(closeDt), false);
        else
            return StringUtils.defaultString(closeDt);
    }

    /**
     * 核定日期
     * 
     * @return
     */
    public String getChkDtString() {
        if (StringUtils.length(chkDt) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(chkDt), false);
        else
            return StringUtils.defaultString(chkDt);
    }

    /**
     * 帳務日期
     * 
     * @return
     */
    public String getAplPayDateString() {
        if (StringUtils.length(aplPayDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(aplPayDate), false);
        else
            return StringUtils.defaultString(aplPayDate);
    }
    
    /**
     * 手冊鑑定日期
     * 
     * @return
     */
    public String getHandIcApDateString() {
        if (StringUtils.length(handIcApDt) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(handIcApDt), false);
        else
            return StringUtils.defaultString(handIcApDt);
    }
    
    /**
     * 首次給付年月
     * 
     * @return
     */
    public String getPayYmsString() {
        if (StringUtils.length(payYms) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYms), false);
        else
            return StringUtils.defaultString(payYms);
    }
    
    /**
     * 首次給付年月
     * 
     * @return
     */
    public String getPayYmeString() {
        if (StringUtils.length(payYme) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYme), false);
        else
            return StringUtils.defaultString(payYme);
    }


    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getEvteeName() {
        return evteeName;
    }

    public void setEvteeName(String evteeName) {
        this.evteeName = evteeName;
    }

    public String getEvtDt() {
        return evtDt;
    }

    public void setEvtDt(String evtDt) {
        this.evtDt = evtDt;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getManChkFlg() {
        return manChkFlg;
    }

    public void setManChkFlg(String manChkFlg) {
        this.manChkFlg = manChkFlg;
    }

    public String getAcceptMark() {
        return acceptMark;
    }

    public void setAcceptMark(String acceptMark) {
        this.acceptMark = acceptMark;
    }

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
        this.chkDt = chkDt;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getCloseDt() {
        return closeDt;
    }

    public void setCloseDt(String closeDt) {
        this.closeDt = closeDt;
    }

	public String getHandIcApDt() {
		return handIcApDt;
	}

	public void setHandIcApDt(String handIcApDt) {
		this.handIcApDt = handIcApDt;
	}

	public String getPayYms() {
		return payYms;
	}

	public void setPayYms(String payYms) {
		this.payYms = payYms;
	}

	public String getPayYme() {
		return payYme;
	}

	public void setPayYme(String payYme) {
		this.payYme = payYme;
	}
    
    
    
    
}
