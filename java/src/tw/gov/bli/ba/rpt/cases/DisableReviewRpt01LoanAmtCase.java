package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單 - 本次紓困貸款資料
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01LoanAmtCase implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;

    private BigDecimal loanAmt; // 勞貸貸款金額
    private BigDecimal recapAmt; // 本金餘額
    private BigDecimal loaniTrt; // 利息
    private String dlineDate; // 本息截止日
    private BigDecimal badDebtAmt; // 呆帳金額
    private BigDecimal offsetExp; // 其他費用
    private BigDecimal offsetAmt; // 抵銷金額

    /**
     * 本息截止日
     */
    public String getDlineDateString() {
        if (StringUtils.length(dlineDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(dlineDate), false);
        else
            return StringUtils.defaultString(dlineDate);
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public BigDecimal getRecapAmt() {
        return recapAmt;
    }

    public void setRecapAmt(BigDecimal recapAmt) {
        this.recapAmt = recapAmt;
    }

    public BigDecimal getLoaniTrt() {
        return loaniTrt;
    }

    public void setLoaniTrt(BigDecimal loaniTrt) {
        this.loaniTrt = loaniTrt;
    }

    public String getDlineDate() {
        return dlineDate;
    }

    public void setDlineDate(String dlineDate) {
        this.dlineDate = dlineDate;
    }

    public BigDecimal getBadDebtAmt() {
        return badDebtAmt;
    }

    public void setBadDebtAmt(BigDecimal badDebtAmt) {
        this.badDebtAmt = badDebtAmt;
    }

    public BigDecimal getOffsetExp() {
        return offsetExp;
    }

    public void setOffsetExp(BigDecimal offsetExp) {
        this.offsetExp = offsetExp;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

}
