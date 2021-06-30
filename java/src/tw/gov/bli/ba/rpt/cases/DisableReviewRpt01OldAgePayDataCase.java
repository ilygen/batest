package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單 - 請領他類給付資料 - 老年年金給付
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01OldAgePayDataCase implements Serializable {
    
    
 // 給付主檔 (BAAPPBASE)
    // [
    private String evtName; // 事故者姓名
    private String appDate; // 申請日期
    private String apNo; // 受理編號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String payYme; 
    private String evtDate; // 事故日期
    private String lsUbno; // 最後單位保險證號
    private BigDecimal issueAmt; // 核定金額
    private String exeDate; // 決行日期
    private String payYms; //首次給付年
    private String closeDate; //結案因期
    private String closeCause; //結案原因
    private String evtJobDate; // 事故日期(失能)
    // ]
    
    //(BAAPPEXPAND)
    private String evTyp;       //傷病分類      
    private String criInJcl1;  //失能等級 1
    private String criInJcl2;  //失能等級 2
    private String criInJcl3;  //失能等級 3
    private String criInJdp1;  //失能項目 1
    private String criInJdp2;  //失能項目 2
    private String criInJdp3;  //失能項目 3
    private String criInJdp4;  //失能項目 4
    private String criInJdp5;  //失能項目 5
    private String criInJdp6;  //失能項目 6
    private String criInJdp7;  //失能項目 7
    private String criInJdp8;  //失能項目 8
    private String criInJdp9;  //失能項目 9
    private String criInJdp10; //失能項目 10

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
    
    
 // 失能等級
    public String getCriInJclStr() {
        String criInJcl = "";
        if (StringUtils.isNotBlank(getCriInJcl1())) {
            criInJcl += getCriInJcl1() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJcl2())) {
            criInJcl += getCriInJcl2() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJcl3())) {
            criInJcl += getCriInJcl3() + ",";
        }
        if (StringUtils.isNotBlank(criInJcl)) {
            criInJcl = criInJcl.substring(0, criInJcl.length() - 1);
        }
        return criInJcl;
    }
    
    // 失能項目
    public String getCriInJdpStr() {
        String criInJdp = "";
        if (StringUtils.isNotBlank(getCriInJdp1())) {
            criInJdp += getCriInJdp1() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp2())) {
            criInJdp += getCriInJdp2() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp3())) {
            criInJdp += getCriInJdp3() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp4())) {
            criInJdp += getCriInJdp4() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp5())) {
            criInJdp += getCriInJdp5() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp6())) {
            criInJdp += getCriInJdp6() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp7())) {
            criInJdp += getCriInJdp7() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp8())) {
            criInJdp += getCriInJdp8() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp9())) {
            criInJdp += getCriInJdp9() + ",";
        }
        if (StringUtils.isNotBlank(getCriInJdp10())) {
            criInJdp += getCriInJdp10() + ",";
        }
        if (StringUtils.isNotBlank(criInJdp)) {
            criInJdp = criInJdp.substring(0, criInJdp.length() - 1);
        }
        return criInJdp;
    }
    
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
     * 事故日期(失能)
     * 
     * @return
     */
    public String getEvtJobDateString() {
        if (StringUtils.length(evtJobDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtJobDate), false);
        else
            return StringUtils.defaultString(evtJobDate);
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

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
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

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getCriInJcl1() {
        return criInJcl1;
    }

    public void setCriInJcl1(String criInJcl1) {
        this.criInJcl1 = criInJcl1;
    }

    public String getCriInJcl2() {
        return criInJcl2;
    }

    public void setCriInJcl2(String criInJcl2) {
        this.criInJcl2 = criInJcl2;
    }

    public String getCriInJcl3() {
        return criInJcl3;
    }

    public void setCriInJcl3(String criInJcl3) {
        this.criInJcl3 = criInJcl3;
    }

    public String getCriInJdp1() {
        return criInJdp1;
    }

    public void setCriInJdp1(String criInJdp1) {
        this.criInJdp1 = criInJdp1;
    }

    public String getCriInJdp2() {
        return criInJdp2;
    }

    public void setCriInJdp2(String criInJdp2) {
        this.criInJdp2 = criInJdp2;
    }

    public String getCriInJdp3() {
        return criInJdp3;
    }

    public void setCriInJdp3(String criInJdp3) {
        this.criInJdp3 = criInJdp3;
    }

    public String getCriInJdp4() {
        return criInJdp4;
    }

    public void setCriInJdp4(String criInJdp4) {
        this.criInJdp4 = criInJdp4;
    }

    public String getCriInJdp5() {
        return criInJdp5;
    }

    public void setCriInJdp5(String criInJdp5) {
        this.criInJdp5 = criInJdp5;
    }

    public String getCriInJdp6() {
        return criInJdp6;
    }

    public void setCriInJdp6(String criInJdp6) {
        this.criInJdp6 = criInJdp6;
    }

    public String getCriInJdp7() {
        return criInJdp7;
    }

    public void setCriInJdp7(String criInJdp7) {
        this.criInJdp7 = criInJdp7;
    }

    public String getCriInJdp8() {
        return criInJdp8;
    }

    public void setCriInJdp8(String criInJdp8) {
        this.criInJdp8 = criInJdp8;
    }

    public String getCriInJdp9() {
        return criInJdp9;
    }

    public void setCriInJdp9(String criInJdp9) {
        this.criInJdp9 = criInJdp9;
    }

    public String getCriInJdp10() {
        return criInJdp10;
    }

    public void setCriInJdp10(String criInJdp10) {
        this.criInJdp10 = criInJdp10;
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

	public String getEvtJobDate() {
		return evtJobDate;
	}

	public void setEvtJobDate(String evtJobDate) {
		this.evtJobDate = evtJobDate;
	}

	public String getPayYme() {
		return payYme;
	}

	public void setPayYme(String payYme) {
		this.payYme = payYme;
	}
    
    

}
