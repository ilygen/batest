package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 勞保老年年金給付受理編審清單 - 核定總額資料
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01IssueAmtDataCase implements Serializable {
    private String veriSeq; // 編審序號
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal adjustAmt; // 物價調整金額
    private BigDecimal supAmt; // 補發金額
    private BigDecimal offsetAmt; // 本次抵銷金額
    private BigDecimal otherAmt; // 事故者扣減總金額
    private BigDecimal aplpayAmt; // 實付總額

    public OldAgeReviewRpt01IssueAmtDataCase() {

    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }

    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

}
