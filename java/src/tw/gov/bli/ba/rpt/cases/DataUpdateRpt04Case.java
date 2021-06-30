package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

public class DataUpdateRpt04Case implements Serializable {
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String appDate;// 申請日期
    private String benIdnNo;// 受益人身分證號
    private String benBrDate;// 受益人出生日期
    private String benName;// 受益人姓名
    private String issueAmt;// 核定金額
    private String evtDieDate;// 事故者死亡日期
    
    private String idn_Aa; // 債務人身份證號
    private String brdte_Aa; // 債務人出生日期(欄位長度七碼，格式為：民國年/月份/日期)
    private String name_Aa; // 債務人姓名
    private String idn_Bb; // 借款人身份證號
    private String brdte_Bb; // 借款人出生日期(欄位長度七碼，格式為：民國年/月份/日期)
    private String name_Bb; // 借款人姓名
    private String rcvNo; // 借款人收件編號
    private BigDecimal amt1; // 借款人呆帳本金
    private BigDecimal amt2; // 借款人呆帳利息
    private BigDecimal amt3; // 借款人違約金
    private BigDecimal amt4; // 借款人費用
    public String getApNo() {
        return apNo;
    }
    public void setApNo(String apNo) {
        this.apNo = apNo;
    }
    public String getSeqNo() {
        return seqNo;
    }
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    public String getAppDate() {
        return appDate;
    }
    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }
    public String getBenIdnNo() {
        return benIdnNo;
    }
    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }
    public String getBenBrDate() {
        return benBrDate;
    }
    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }
    public String getBenName() {
        return benName;
    }
    public void setBenName(String benName) {
        this.benName = benName;
    }
    public String getIssueAmt() {
        return issueAmt;
    }
    public void setIssueAmt(String issueAmt) {
        this.issueAmt = issueAmt;
    }
    public String getEvtDieDate() {
        return evtDieDate;
    }
    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }
    public String getIdn_Aa() {
        return idn_Aa;
    }
    public void setIdn_Aa(String idn_Aa) {
        this.idn_Aa = idn_Aa;
    }
    public String getBrdte_Aa() {
        return brdte_Aa;
    }
    public void setBrdte_Aa(String brdte_Aa) {
        this.brdte_Aa = brdte_Aa;
    }
    public String getName_Aa() {
        return name_Aa;
    }
    public void setName_Aa(String name_Aa) {
        this.name_Aa = name_Aa;
    }
    public String getIdn_Bb() {
        return idn_Bb;
    }
    public void setIdn_Bb(String idn_Bb) {
        this.idn_Bb = idn_Bb;
    }
    public String getBrdte_Bb() {
        return brdte_Bb;
    }
    public void setBrdte_Bb(String brdte_Bb) {
        this.brdte_Bb = brdte_Bb;
    }
    public String getName_Bb() {
        return name_Bb;
    }
    public void setName_Bb(String name_Bb) {
        this.name_Bb = name_Bb;
    }
    public String getRcvNo() {
        return rcvNo;
    }
    public void setRcvNo(String rcvNo) {
        this.rcvNo = rcvNo;
    }
    public BigDecimal getAmt1() {
        return amt1;
    }
    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }
    public BigDecimal getAmt2() {
        return amt2;
    }
    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }
    public BigDecimal getAmt3() {
        return amt3;
    }
    public void setAmt3(BigDecimal amt3) {
        this.amt3 = amt3;
    }
    public BigDecimal getAmt4() {
        return amt4;
    }
    public void setAmt4(BigDecimal amt4) {
        this.amt4 = amt4;
    }
    public String getPayCodeStr() {
        if(apNo.equals("") || apNo.equals(null)){
            return "";
        } else {
            if(apNo.substring(0, 1).equals("L") )
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
            else if(apNo.substring(0, 1).equals("K"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
            else if(apNo.substring(0, 1).equals("S"))
                return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
            else
                return "";          
        }
    }
    public String getBrdteAaStr() {
        return DateUtility.formatChineseDateString(DateUtility.changeDateType(brdte_Aa), false);
    }
    public String getBrdteBbStr() {
        return DateUtility.formatChineseDateString(DateUtility.changeDateType(brdte_Bb), false);
    }
    public String getAppDateStr() {
        return DateUtility.formatChineseDateString(DateUtility.changeDateType(appDate), false);
    }
    public String getDeptNameString() {
        String payKindCode = StringUtils.substring(apNo, 0, 1);

        if (StringUtils.equalsIgnoreCase(payKindCode, "L"))
            return "生老";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "K"))
            return "傷殘";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "S"))
            return "死亡";
        else
            return "";
    }
    public String getApNoStr() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
}
