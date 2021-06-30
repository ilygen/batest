package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 應收款立帳核定清單
 * 
 * @author Rickychi
 */
public class MonthlyRpt20Case implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private BigDecimal apNoAmt;// 件數
    private BigDecimal dataAmt;// 筆數
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal unacpAmt;// 應收金額
    private String chkDate;// 核定日期

    // Footer使用
    private String rptTyp;
    private String payCode;
    private String issuYm;
    // private String payTyp;
    // private String chkDate;
    private String benEvtRel;
    private BigDecimal accountSeq;
    private BigDecimal seqNo;
    private String accountNo;
    private String accountName;
    private BigDecimal payAmt;
    private String paySeqNo;
    private String nlWkMk;
    private String adWkMk;
    private String naChgMk;
    private String procTime;

    private BigDecimal rptPage; // 清冊頁次
    private BigDecimal eRptPage; // 清冊頁次
    private String cPrnDate; // 印表日期

    /**
     * 印表日期
     * 
     * @return
     */
    public String getcPrnDateStr() {
        if (StringUtils.isNotBlank(getcPrnDate()))
            return DateUtility.changeDateType(getcPrnDate());
        else
            return "";
    }

    // 頁面顯示轉換
    // [
    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate()))
            return DateUtility.changeDateType(getChkDate());
        else
            return "";
    }

    // ]

    public BigDecimal getApNoAmt() {
        return apNoAmt;
    }

    public void setApNoAmt(BigDecimal apNoAmt) {
        this.apNoAmt = apNoAmt;
    }

    public BigDecimal getDataAmt() {
        return dataAmt;
    }

    public void setDataAmt(BigDecimal dataAmt) {
        this.dataAmt = dataAmt;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getUnacpAmt() {
        return unacpAmt;
    }

    public void setUnacpAmt(BigDecimal unacpAmt) {
        this.unacpAmt = unacpAmt;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    // Footer使用

    public String getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public BigDecimal getAccountSeq() {
        return accountSeq;
    }

    public void setAccountSeq(BigDecimal accountSeq) {
        this.accountSeq = accountSeq;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getPaySeqNo() {
        return paySeqNo;
    }

    public void setPaySeqNo(String paySeqNo) {
        this.paySeqNo = paySeqNo;
    }

    public String getNlWkMk() {
        return nlWkMk;
    }

    public void setNlWkMk(String nlWkMk) {
        this.nlWkMk = nlWkMk;
    }

    public String getAdWkMk() {
        return adWkMk;
    }

    public void setAdWkMk(String adWkMk) {
        this.adWkMk = adWkMk;
    }

    public String getNaChgMk() {
        return naChgMk;
    }

    public void setNaChgMk(String naChgMk) {
        this.naChgMk = naChgMk;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }

    public BigDecimal getRptPage() {
        return rptPage;
    }

    public void setRptPage(BigDecimal rptPage) {
        this.rptPage = rptPage;
    }

    public BigDecimal geteRptPage() {
        return eRptPage;
    }

    public void seteRptPage(BigDecimal eRptPage) {
        this.eRptPage = eRptPage;
    }

    public String getcPrnDate() {
        return cPrnDate;
    }

    public void setcPrnDate(String cPrnDate) {
        this.cPrnDate = cPrnDate;
    }

}
