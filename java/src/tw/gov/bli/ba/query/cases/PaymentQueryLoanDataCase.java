package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 紓困貸款
 * 
 * @author Rickychi
 */
public class PaymentQueryLoanDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private BigDecimal loanAmt; // 勞貸貸款金額
    private BigDecimal recapAmt; // 本次勞貸本金餘額
    private BigDecimal loaniTrt; // 本次勞貸利息
    private String dlineDate; // 本次勞貸本息截止日
    private BigDecimal badDebtAmt; // 呆帳金額
    private BigDecimal offsetExp; // 本次抵銷勞貸其他費用
    private BigDecimal offsetAmt; // 本次抵銷金額
    // 頁面顯示轉換
    // [

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm()) && getIssuYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return getIssuYm();
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm()) && getPayYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getPayYm());
        }
        else {
            return getPayYm();
        }
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getDlineDateStr() {
        if (StringUtils.isNotBlank(getDlineDate())) {
            return DateUtility.changeDateType(getDlineDate());
        }
        else {
            return "";
        }
    }

    // ]

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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

}
