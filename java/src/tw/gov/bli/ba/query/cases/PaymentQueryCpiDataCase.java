package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 物價指數調整
 * 
 * @author Rickychi
 */
public class PaymentQueryCpiDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private BigDecimal befIssueAmt; // 核定金額
    private BigDecimal issuCalcAmt; // 物價調整前金額
    private BigDecimal cpiRate; // 核定計算金額物價調整指數

    private String nowPayYm;// 本次給付年
    private BigDecimal lastIssueAmt; // 前次核定金額
    private BigDecimal nowIssueAmt; // 本次核定金額

    private String issuYear; // 核定年度
    private String adjYear; // 申請年度
    private BigDecimal adjCpi; // 調整指數
    private String cpiYearB; // 指數年度（起）
    private String cpiYearE; // 指數年度（迄）
    private String adjYmB; // 調整起月

    // 頁面顯示轉換
    // [

    public String getNowPayYmStr() {
        if (StringUtils.isNotBlank(getNowPayYm()) && getNowPayYm().length() == 6) {
            return DateUtility.changeWestYearMonthType(getNowPayYm());
        }
        else {
            return getNowPayYm();
        }
    }

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

    public String getAdjYearStr() {
        if (StringUtils.isNotBlank(adjYear) && adjYear.length() == 4) {
            return StringUtils.leftPad(String.valueOf(Integer.parseInt(adjYear) - 1911), 3, "0");
        }
        else {
            return adjYear;
        }
    }

    public String getIssuYearStr() {
        if (StringUtils.isNotBlank(issuYear) && issuYear.length() == 4) {
            return StringUtils.leftPad(String.valueOf(Integer.parseInt(issuYear) - 1911), 3, "0");
        }
        else {
            return issuYear;
        }
    }

    public String getCpiYearBStr() {
        if (StringUtils.isNotBlank(cpiYearB) && cpiYearB.length() == 4) {
            return StringUtils.leftPad(String.valueOf(Integer.parseInt(cpiYearB) - 1911), 3, "0");
        }
        else {
            return cpiYearB;
        }
    }

    public String getCpiYearEStr() {
        if (StringUtils.isNotBlank(cpiYearE) && cpiYearE.length() == 4) {
            return StringUtils.leftPad(String.valueOf(Integer.parseInt(cpiYearE) - 1911), 3, "0");
        }
        else {
            return cpiYearE;
        }
    }

    public String getAdjYmBStr() {
        if (StringUtils.isNotBlank(adjYmB) && adjYmB.length() == 6) {
            return DateUtility.changeWestYearMonthType(adjYmB);
        }
        else {
            return adjYmB;
        }
    }

    // ]

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }

    public BigDecimal getIssuCalcAmt() {
        return issuCalcAmt;
    }

    public void setIssuCalcAmt(BigDecimal issuCalcAmt) {
        this.issuCalcAmt = issuCalcAmt;
    }

    public BigDecimal getCpiRate() {
        return cpiRate;
    }

    public void setCpiRate(BigDecimal cpiRate) {
        this.cpiRate = cpiRate;
    }

    public String getAdjYear() {
        return adjYear;
    }

    public void setAdjYear(String adjYear) {
        this.adjYear = adjYear;
    }

    public BigDecimal getAdjCpi() {
        return adjCpi;
    }

    public void setAdjCpi(BigDecimal adjCpi) {
        this.adjCpi = adjCpi;
    }

    public String getCpiYearB() {
        return cpiYearB;
    }

    public void setCpiYearB(String cpiYearB) {
        this.cpiYearB = cpiYearB;
    }

    public String getCpiYearE() {
        return cpiYearE;
    }

    public void setCpiYearE(String cpiYearE) {
        this.cpiYearE = cpiYearE;
    }

    public String getAdjYmB() {
        return adjYmB;
    }

    public void setAdjYmB(String adjYmB) {
        this.adjYmB = adjYmB;
    }

    public String getNowPayYm() {
        return nowPayYm;
    }

    public void setNowPayYm(String nowPayYm) {
        this.nowPayYm = nowPayYm;
    }

    public BigDecimal getLastIssueAmt() {
        return lastIssueAmt;
    }

    public void setLastIssueAmt(BigDecimal lastIssueAmt) {
        this.lastIssueAmt = lastIssueAmt;
    }

    public BigDecimal getNowIssueAmt() {
        return nowIssueAmt;
    }

    public void setNowIssueAmt(BigDecimal nowIssueAmt) {
        this.nowIssueAmt = nowIssueAmt;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getIssuYear() {
        return issuYear;
    }

    public void setIssuYear(String issuYear) {
        this.issuYear = issuYear;
    }

}
