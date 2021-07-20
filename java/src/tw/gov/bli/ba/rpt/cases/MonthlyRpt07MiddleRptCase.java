package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 月核定給付撥款總額表
 * 
 * @author Goston
 */
public class MonthlyRpt07MiddleRptCase implements Serializable {
    private String bankNo; // 行庫局
    private String bankName; // 名稱
    private BigDecimal dataCount; // 筆數
    private BigDecimal amountAmt; // 金額

    public MonthlyRpt07MiddleRptCase() {

    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
