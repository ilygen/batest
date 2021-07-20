package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 請領同類給付資料 - 年金給付資料
 * 
 * @author Rickychi
 */
public class SurvivorReviewRpt01AnnuityPayDataCase implements Serializable {
    // 給付主檔 (BAAPPBASE)
    // [
    private String evtName; // 事故者姓名
    private String appDate; // 申請日期
    private String apNo; // 受理編號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String payYme;
    private String payYms;
    private String evtDate; // 事故日期
    private String lsUbno; // 最後單位保險證號
    private BigDecimal issueAmt; // 核定金額
    private String exeDate; // 決行日期
    private String closeDate;//結案日期
    private String closeCause; //結案原因
    // ]

    // 給付核定檔 (BADAPR)
    // [
    private String chkDate; // 核定日期
    private String aplpayDate; // 核付日期
    private BigDecimal recAmt; // 收回金額
    private BigDecimal supAmt; // 補發金額

    // ]

    // 行政支援記錄檔 (MAADMREC)
    // [
    private String prodate; // 補件日期
    private String ndomk1; // 處理註記

    // ]
    
    // 給付延伸主檔 (BAAPPEXPAND)
    // [
    private String evTyp; //傷病分類
   

    // ]
    
    

    /**
     * 申請日期
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
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
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
     * 給付年月
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
     * 給付年月
     * 
     * @return
     */
    public String getPayYmeString() {
        if (StringUtils.length(payYme) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYme), false);
        else
            return StringUtils.defaultString(payYme);
    }


    /**
     * 事故日期
     * 
     * @return
     */
    public String getEvtDateString() {
        if (StringUtils.length(evtDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtDate), false);
        else
            return StringUtils.defaultString(evtDate);
    }

    /**
     * 決行日期
     * 
     * @return
     */
    public String getExeDateString() {
        if (StringUtils.length(exeDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(exeDate), false);
        else
            return StringUtils.defaultString(exeDate);
    }

    /**
     * 核定日期
     * 
     * @return
     */
    public String getChkDateString() {
        if (StringUtils.length(chkDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(chkDate), false);
        else
            return StringUtils.defaultString(chkDate);
    }
    
    
    /**
     *  結案日期
     * 
     * @return
     */
    public String getCloseDtString() {
        if (StringUtils.length(closeDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(closeDate), false);
        else
            return StringUtils.defaultString(closeDate);
    }


    /**
     * 帳務日期
     * 
     * @return
     */
    public String getAplpayDateString() {
        if (StringUtils.length(aplpayDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(aplpayDate), false);
        else
            return StringUtils.defaultString(aplpayDate);
    }

    /**
     * 補件日期
     * 
     * @return
     */
    public String getProdateString() {
        if (StringUtils.length(prodate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(prodate), false);
        else
            return StringUtils.defaultString(prodate);
    }

    public SurvivorReviewRpt01AnnuityPayDataCase() {

    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
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

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getAplpayDate() {
        return aplpayDate;
    }

    public void setAplpayDate(String aplpayDate) {
        this.aplpayDate = aplpayDate;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public String getProdate() {
        return prodate;
    }

    public void setProdate(String prodate) {
        this.prodate = prodate;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
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

	public String getPayYme() {
		return payYme;
	}

	public void setPayYme(String payYme) {
		this.payYme = payYme;
	}

	public String getPayYms() {
		return payYms;
	}

	public void setPayYms(String payYms) {
		this.payYms = payYms;
	}

    
}
