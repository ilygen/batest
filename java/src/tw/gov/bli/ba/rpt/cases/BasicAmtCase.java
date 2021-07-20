package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.math.IntRange;

public class BasicAmtCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    IntRange ymRange;// 基本金額給付年月區間
    BigDecimal baseAmt;// 基本金額

    public IntRange getYmRange() {
        return ymRange;
    }

    public void setYmRange(IntRange ymRange) {
        this.ymRange = ymRange;
    }

    public BigDecimal getBaseAmt() {
        return baseAmt;
    }

    public void setBaseAmt(BigDecimal baseAmt) {
        this.baseAmt = baseAmt;
    }

}
