package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 月核定撥付總表檔
 * 
 * @author swim
 */
@Table("BALP0D340")
public class Balp0d340 implements Serializable {
    @PkeyField("PAYCODE")
    private String payCode; // 給付別
    @PkeyField("ISSUYM")
    private String issuYm; // 核定年月
    private String caseTyp; // 案件類別
    private BigDecimal caseCount; // 受理件數
    private BigDecimal paymCount; // 給付月數
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal aplpayAmt; // 實付金額
    private String crtUser;
    private String crtTime;
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
    public String getCaseTyp() {
        return caseTyp;
    }
    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }
    public BigDecimal getCaseCount() {
        return caseCount;
    }
    public void setCaseCount(BigDecimal caseCount) {
        this.caseCount = caseCount;
    }
    public BigDecimal getPaymCount() {
        return paymCount;
    }
    public void setPaymCount(BigDecimal paymCount) {
        this.paymCount = paymCount;
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
    public String getCrtUser() {
        return crtUser;
    }
    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }
    public String getCrtTime() {
        return crtTime;
    }
    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }
    
}
