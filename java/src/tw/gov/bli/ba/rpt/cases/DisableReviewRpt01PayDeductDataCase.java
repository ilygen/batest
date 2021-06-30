package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class DisableReviewRpt01PayDeductDataCase implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;

    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String issuYm; // 核定年月
    private String payYm; // 核付年月
    private BigDecimal recAmt; // 應收總額
    private BigDecimal recRem; // 未收餘額
    private String benName; // 立帳對象

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

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

}
