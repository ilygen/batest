package tw.gov.bli.ba.update.forms;

import java.math.BigDecimal;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class StopPaymentProcessDetailForm extends BaseValidatorForm {
    private String method;

    private String appDate; // 申請日期
    private String apNo; // 受理編號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    private String evtIdnNo; // 事故者身分證號
    private String procStat; // 處理狀態
    private String promoteUser; // 承辦人員
    private String benName; // 受益人姓名
    private String benIdnNo; // 受益人身分證號
    private String benEvtRel; // 受益人與事故者關係
    private String benBrDate; // 受款人出生日期
    private String payTyp; // 給付方式

    private String issuYm; // 核定年月
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal aplpayAmt; // 實付金額
    private String stexpndMk; // 止付註記
    private String seqNo; // 序號
    private String chkDate; // 核定日期
    private String stexpndReason; // 止付原因
    private String stexpndDate; // 止付日期

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStexpndReason() {
        return stexpndReason;
    }

    public void setStexpndReason(String stexpndReason) {
        this.stexpndReason = stexpndReason;
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

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }

    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }

    public String getStexpndMk() {
        return stexpndMk;
    }

    public void setStexpndMk(String stexpndMk) {
        this.stexpndMk = stexpndMk;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getStexpndDate() {
        return stexpndDate;
    }

    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }
}
