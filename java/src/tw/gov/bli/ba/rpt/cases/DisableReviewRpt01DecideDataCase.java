package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 勞保失能年金給付受理編審清單 - 核定資料
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01DecideDataCase implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String veriSeq; // 編審序號
    private BigDecimal insAvgAmt; // 平均薪資
    private BigDecimal itrmY; // 投保年資 (年)
    private BigDecimal itrmD; // 投保年資 (日)
    private BigDecimal nitrmY; // 投保年資 (年 - 年金制)
    private BigDecimal nitrmM; // 投保年資 (月 - 年金制)
    private BigDecimal noldtY; // 老年年資 (年)
    private BigDecimal noldtM; // 老年年資 (月)
    private BigDecimal valseniY; // 國保已繳年資 (年)
    private BigDecimal valseniM; // 國保已繳年資 (月)
    private BigDecimal aplPaySeniY; // 實付年資 (年)
    private BigDecimal aplPaySeniM; // 實付年資 (月)
    private String oldab; // 第一式 / 第二式
    private BigDecimal oldaAmt; // 第一式式金額
    private BigDecimal oldbAmt; // 第二式式金額
    private BigDecimal oldRate; // 展延 / 減額比率
    private String oldRateSdate; // 展延 / 減額期間 (起)
    private String oldRateEdate; // 展延 / 減額期間 (迄)
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal adjustAmt; // 調整金額
    private BigDecimal recAmt; // 收回金額
    private BigDecimal supAmt; // 補發金額
    private BigDecimal aplpayAmt; // 實付金額
    private BigDecimal annuAmt; // 累計已領年金金額

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

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public BigDecimal getItrmY() {
        return itrmY;
    }

    public void setItrmY(BigDecimal itrmY) {
        this.itrmY = itrmY;
    }

    public BigDecimal getItrmD() {
        return itrmD;
    }

    public void setItrmD(BigDecimal itrmD) {
        this.itrmD = itrmD;
    }

    public BigDecimal getNitrmY() {
        return nitrmY;
    }

    public void setNitrmY(BigDecimal nitrmY) {
        this.nitrmY = nitrmY;
    }

    public BigDecimal getNitrmM() {
        return nitrmM;
    }

    public void setNitrmM(BigDecimal nitrmM) {
        this.nitrmM = nitrmM;
    }

    public BigDecimal getNoldtY() {
        return noldtY;
    }

    public void setNoldtY(BigDecimal noldtY) {
        this.noldtY = noldtY;
    }

    public BigDecimal getNoldtM() {
        return noldtM;
    }

    public void setNoldtM(BigDecimal noldtM) {
        this.noldtM = noldtM;
    }

    public BigDecimal getValseniY() {
        return valseniY;
    }

    public void setValseniY(BigDecimal valseniY) {
        this.valseniY = valseniY;
    }

    public BigDecimal getValseniM() {
        return valseniM;
    }

    public void setValseniM(BigDecimal valseniM) {
        this.valseniM = valseniM;
    }

    public BigDecimal getAplPaySeniY() {
        return aplPaySeniY;
    }

    public void setAplPaySeniY(BigDecimal aplPaySeniY) {
        this.aplPaySeniY = aplPaySeniY;
    }

    public BigDecimal getAplPaySeniM() {
        return aplPaySeniM;
    }

    public void setAplPaySeniM(BigDecimal aplPaySeniM) {
        this.aplPaySeniM = aplPaySeniM;
    }

    public String getOldab() {
        return oldab;
    }

    public void setOldab(String oldab) {
        this.oldab = oldab;
    }

    public BigDecimal getOldaAmt() {
        return oldaAmt;
    }

    public void setOldaAmt(BigDecimal oldaAmt) {
        this.oldaAmt = oldaAmt;
    }

    public BigDecimal getOldbAmt() {
        return oldbAmt;
    }

    public void setOldbAmt(BigDecimal oldbAmt) {
        this.oldbAmt = oldbAmt;
    }

    public BigDecimal getOldRate() {
        return oldRate;
    }

    public void setOldRate(BigDecimal oldRate) {
        this.oldRate = oldRate;
    }

    public String getOldRateSdate() {
        return oldRateSdate;
    }

    public void setOldRateSdate(String oldRateSdate) {
        this.oldRateSdate = oldRateSdate;
    }

    public String getOldRateEdate() {
        return oldRateEdate;
    }

    public void setOldRateEdate(String oldRateEdate) {
        this.oldRateEdate = oldRateEdate;
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

    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }

    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

}
