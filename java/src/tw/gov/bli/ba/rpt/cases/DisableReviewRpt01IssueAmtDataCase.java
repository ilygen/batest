package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 勞保失能年金給付受理編審清單 - 核定總額資料
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01IssueAmtDataCase implements Serializable{
    
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal adjustAmt; // 物價調整金額
    private BigDecimal supAmt; // 補發金額
    private BigDecimal otherAmt; // 事故者扣減總金額
    private BigDecimal aplpayAmt; // 實付總額
    private BigDecimal offsetAmt; // 紓困總額
    
    public DisableReviewRpt01IssueAmtDataCase() {

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

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }
    
    

}
