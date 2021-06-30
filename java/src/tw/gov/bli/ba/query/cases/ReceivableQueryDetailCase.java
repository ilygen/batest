package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 應收查詢作業 Detail
 * 
 * @author Rickychi
 */
public class ReceivableQueryDetailCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String apNo; // 受理編號
    private String evtIdnNo; // 事故者身分證號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String unacpDesc; // 應收原因代號
    private String unapcKind; // (應收)收回種類
    private String procStat; // (應收)處理狀態
    private String unacpDate; // 應收立帳日期
    private String aplPayDate; // 入帳日期
    private BigDecimal recAmt; // 應收金額
    private BigDecimal unapcDtlNo; // 收回序號
    private String recProcStat; // (已收)處理狀態
    private String recKind; // (已收)收回種類
    private String recTyp; // 收回方式
    private String recDate; // 收回日期
    private String recAplPayDate; // 入帳日期
    private BigDecimal woAmt; // 收回金額
    private BigDecimal recWoAmt; // 應收餘額

    // 頁面顯示轉換
    // [
    public String getApNoStr() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getEvtBrDate());
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

    public String getUnacpDateStr() {
        if (StringUtils.isNotBlank(getUnacpDate())) {
            return DateUtility.changeDateType(getUnacpDate());
        }
        else {
            return "";
        }
    }

    public String getAplPayDateStr() {
        if (StringUtils.isNotBlank(getAplPayDate())) {
            return DateUtility.changeDateType(getAplPayDate());
        }
        else {
            return "";
        }
    }

    public String getRecDateStr() {
        if (StringUtils.isNotBlank(getRecDate())) {
            return DateUtility.changeDateType(getRecDate());
        }
        else {
            return "";
        }
    }

    public String getRecAplPayDateStr() {
        if (StringUtils.isNotBlank(getRecAplPayDate())) {
            return DateUtility.changeDateType(getRecAplPayDate());
        }
        else {
            return "";
        }
    }

    // ]
    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
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

    public String getUnacpDesc() {
        return unacpDesc;
    }

    public void setUnacpDesc(String unacpDesc) {
        this.unacpDesc = unacpDesc;
    }

    public String getUnapcKind() {
        return unapcKind;
    }

    public void setUnapcKind(String unapcKind) {
        this.unapcKind = unapcKind;
    }

    public String getUnacpDate() {
        return unacpDate;
    }

    public void setUnacpDate(String unacpDate) {
        this.unacpDate = unacpDate;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getUnapcDtlNo() {
        return unapcDtlNo;
    }

    public void setUnapcDtlNo(BigDecimal unapcDtlNo) {
        this.unapcDtlNo = unapcDtlNo;
    }

    public String getRecProcStat() {
        return recProcStat;
    }

    public void setRecProcStat(String recProcStat) {
        this.recProcStat = recProcStat;
    }

    public String getRecKind() {
        return recKind;
    }

    public void setRecKind(String recKind) {
        this.recKind = recKind;
    }

    public String getRecTyp() {
        return recTyp;
    }

    public void setRecTyp(String recTyp) {
        this.recTyp = recTyp;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate;
    }

    public String getRecAplPayDate() {
        return recAplPayDate;
    }

    public void setRecAplPayDate(String recAplPayDate) {
        this.recAplPayDate = recAplPayDate;
    }

    public BigDecimal getWoAmt() {
        return woAmt;
    }

    public void setWoAmt(BigDecimal woAmt) {
        this.woAmt = woAmt;
    }

    public BigDecimal getRecWoAmt() {
        return recWoAmt;
    }

    public void setRecWoAmt(BigDecimal recWoAmt) {
        this.recWoAmt = recWoAmt;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }
}
