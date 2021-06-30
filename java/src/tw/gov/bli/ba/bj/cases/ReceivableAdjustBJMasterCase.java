package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * case for 已收調整作業 Master
 * 
 * @author Rickychi
 */
public class ReceivableAdjustBJMasterCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private BigDecimal baunacprecId;// 應收記錄編號
    private String apNo; // 受理編號
    private String recKind; // 收回方式
    private String benIdnNo; // 受款人身份證號
    private String benName; // 受款人姓名
    private String aplPayDate; // 入帳日期
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private BigDecimal recAmt; // 應收金額
    private BigDecimal woAmt; // 已收金額
    private String befBenName; // 更正前受款人姓名
    private String befBenIdnNo; // 更正前受款人身份證號

    // 頁面顯示轉換
    // [
    public String getApNoStr() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public String getAplPayDateStr() {
        if (StringUtils.isNotBlank(getAplPayDate())) {
            return DateUtility.changeDateType(getAplPayDate());
        }
        else {
            return "";
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm()) && getIssuYm().length() == 6) {
            return DateUtility.changeDateType(getIssuYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm()) && getPayYm().length() == 6) {
            return DateUtility.changeDateType(getPayYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    // ]

    public BigDecimal getBaunacprecId() {
        return baunacprecId;
    }

    public void setBaunacprecId(BigDecimal baunacprecId) {
        this.baunacprecId = baunacprecId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getRecKind() {
        return recKind;
    }

    public void setRecKind(String recKind) {
        this.recKind = recKind;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
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

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getWoAmt() {
        return woAmt;
    }

    public void setWoAmt(BigDecimal woAmt) {
        this.woAmt = woAmt;
    }

    public String getBefBenName() {
        return befBenName;
    }

    public void setBefBenName(String befBenName) {
        this.befBenName = befBenName;
    }

    public String getBefBenIdnNo() {
        return befBenIdnNo;
    }

    public void setBefBenIdnNo(String befBenIdnNo) {
        this.befBenIdnNo = befBenIdnNo;
    }
}
