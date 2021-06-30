package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保老年年金給付受理編審清單 - 請領同類給付資料 - 年金給付
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01AnnuityPayDataCase implements Serializable {
    // 給付主檔 (BAAPPBASE)
    // [
    private String evtName; // 事故者姓名
    private String appDate; // 申請日期
    private String apNo; // 受理編號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String evtJobDate; // 事故日期
    private String lsUbno; // 最後單位保險證號
    private BigDecimal issueAmt; // 核定金額
    private String exeDate; // 決行日期
    private String closeDate;// 結案日期
    private String closeCause;// 結案原因
    private String caseTyp;// 案件類別
    private String procStat;// 處理狀態
    
    // ]

    // 給付核定檔 (BADAPR)
    // [
    private String chkDate; // 核定日期
    private String aplpayDate; // 帳務日期
    private BigDecimal recAmt; // 收回金額
    private BigDecimal supAmt; // 補發金額
    // ]

    // 行政支援記錄檔 (MAADMREC)
    // [
    private String prodate; // 承辦 / 創收日期
    private String ndomk1; // 處理註記一

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
     * 結案日期
     * 
     * @return
     */
    public String getCloseDateString() {
        if (StringUtils.length(closeDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(closeDate), false);
        else
            return StringUtils.defaultString(closeDate);
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
     * 事故日期
     * 
     * @return
     */
    public String getEvtJobDateString() {
        if (StringUtils.length(evtJobDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtJobDate), false);
        else
            return StringUtils.defaultString(evtJobDate);
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
     * 承辦 / 創收日期
     * 
     * @return
     */
    public String getProdateString() {
        if (StringUtils.length(prodate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(prodate), false);
        else
            return StringUtils.defaultString(prodate);
    }
    
    /**
     * 案件類別
     */    
    public String getCaseTypString() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else
            return "";
    }
    
    /**
     * 處理狀態
     */  
    public String getProcStatString() {
        if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_00))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_00;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_01))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_01;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_10))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_10;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_11))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_11;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_12))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_12;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_13))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_13;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_20))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_20;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_30))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_30;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_40))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_40;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_50))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_50;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_80))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_80;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_90))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_99))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
        else
            return "";
    }

    public OldAgeReviewRpt01AnnuityPayDataCase() {

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

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
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

}
