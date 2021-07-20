package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 核付明細表 (代扣補償金)
 * 
 * @author Goston
 */
public class MonthlyRpt10Type2PayAmtCase implements Serializable {
    private String compName1; // 相關單位名稱1
    private BigDecimal payAmt; // 金額

    public MonthlyRpt10Type2PayAmtCase() {

    }

    public String getCompName1() {
        return compName1;
    }

    public void setCompName1(String compName1) {
        this.compName1 = compName1;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

}
