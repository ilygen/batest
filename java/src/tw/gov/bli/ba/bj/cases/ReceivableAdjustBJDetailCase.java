package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * case for 已收調整作業 Detail
 * 
 * @author Rickychi
 */
public class ReceivableAdjustBJDetailCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private BigDecimal baunacprecId; // 應收記錄編號
    private String apNo; // 受理編號
    private String benIdnNo; // 受款人身份證號
    private String benName; // 受款人姓名
    private String recKind; // (更正後)收回方式
    private String issuYm; // (更正後)核定年月
    private String payYm; // (更正後)給付年月
    private BigDecimal recAmt; // (更正後)應收金額
    private BigDecimal woAmt; // (更正後)已收金額
    private String befBenName; // (更正前)受款人姓名
    private String befBenIdnNo; // (更正前)受款人身份證號
    private String befRecKind; // (更正前)收回方式
    private String befIssuYm; // (更正前)核定年月
    private String befPayYm; // (更正前)給付年月
    private BigDecimal befRecAmt; // (更正前)應收金額
    private BigDecimal befWoAmt; // (更正前)已收金額

    // 頁面顯示轉換
    // [
    public String getApNoStr() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
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

    public String getBefIssuYmStr() {
        if (StringUtils.isNotBlank(getBefIssuYm()) && getBefIssuYm().length() == 6) {
            return DateUtility.changeDateType(getBefIssuYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getBefPayYmStr() {
        if (StringUtils.isNotBlank(getBefPayYm()) && getBefPayYm().length() == 6) {
            return DateUtility.changeDateType(getBefPayYm() + "01").substring(0, 5);
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

    public String getRecKind() {
        return recKind;
    }

    public void setRecKind(String recKind) {
        this.recKind = recKind;
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

    public String getBefRecKind() {
        return befRecKind;
    }

    public void setBefRecKind(String befRecKind) {
        this.befRecKind = befRecKind;
    }

    public String getBefIssuYm() {
        return befIssuYm;
    }

    public void setBefIssuYm(String befIssuYm) {
        this.befIssuYm = befIssuYm;
    }

    public String getBefPayYm() {
        return befPayYm;
    }

    public void setBefPayYm(String befPayYm) {
        this.befPayYm = befPayYm;
    }

    public BigDecimal getBefRecAmt() {
        return befRecAmt;
    }

    public void setBefRecAmt(BigDecimal befRecAmt) {
        this.befRecAmt = befRecAmt;
    }

    public BigDecimal getBefWoAmt() {
        return befWoAmt;
    }

    public void setBefWoAmt(BigDecimal befWoAmt) {
        this.befWoAmt = befWoAmt;
    }

}
