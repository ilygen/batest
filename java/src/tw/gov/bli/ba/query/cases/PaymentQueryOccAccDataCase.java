package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 給付查詢作業 明細資料 - 職災相關資料
 * 
 * @author Rickychi
 */
public class PaymentQueryOccAccDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
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
