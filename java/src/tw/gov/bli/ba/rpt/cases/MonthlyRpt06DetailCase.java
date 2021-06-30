package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthlyRpt06DetailCase implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private String veriSeq; // 版次
    private String payYmBeg; // 給付年月(起)
    private String payYmEnd; // 給付年月(迄)
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal aplpayAmt; // 實付金額

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public String getPayYmBeg() {
        return payYmBeg;
    }

    public void setPayYmBeg(String payYmBeg) {
        this.payYmBeg = payYmBeg;
    }

    public String getPayYmEnd() {
        return payYmEnd;
    }

    public void setPayYmEnd(String payYmEnd) {
        this.payYmEnd = payYmEnd;
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
}
