package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 職災相關資料
 * 
 * @author Evelyn Hsu
 */

public class SurvivorQueryOccAccDataCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;

    private BigDecimal insAvgAmt; // 6個月平均薪資
    private BigDecimal befIssueAmt; // 職災補償1次金
    private BigDecimal aplpayAmt; // 實發職災1次金
    private BigDecimal ocAccaddAmt; // 已領職災增給金額

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }

    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }

    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }

    public BigDecimal getOcAccaddAmt() {
        return ocAccaddAmt;
    }

    public void setOcAccaddAmt(BigDecimal ocAccaddAmt) {
        this.ocAccaddAmt = ocAccaddAmt;
    }

}
