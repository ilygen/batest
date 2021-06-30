package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 月核定給付撥款總額表
 * 
 * @author Goston
 */
public class MonthlyRpt07FooterRptCase implements Serializable {
    private String payNo; // 給付代號
    private String payName; // 給付名稱
    private BigDecimal dataCount; // 筆數
    private BigDecimal amountAmt; // 金額

    public MonthlyRpt07FooterRptCase() {

    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public BigDecimal getAmountAmt() {
        return amountAmt;
    }

    public void setAmountAmt(BigDecimal amountAmt) {
        this.amountAmt = amountAmt;
    }

}
