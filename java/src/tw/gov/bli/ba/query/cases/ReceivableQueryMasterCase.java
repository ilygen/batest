package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 應收查詢作業 Master
 * 
 * @author Rickychi
 */
public class ReceivableQueryMasterCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String apNo; // 受理編號
    private String recKind; // 收回種類
    private String evtIdnNo; // 事故者身分證號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    private String benIdnNo; // 受款人身分證號
    private String benName; // 受款人姓名
    private String benBrDate; // 受款人出生日期
    private String unacpDate; // 應收立帳日期
    private String procStat; // 處理狀態
    private BigDecimal recAmt; // 應收總金額
    private BigDecimal woAmt; // 收回總金額
    private BigDecimal realRecAmt; // 實收金額
    private BigDecimal recRem; // 未收金額

    private String pagePayKind;// 給付別
    private String unacpSdate;// 應收立帳起日
    private String unacpEdate;// 應收立帳迄日

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

    public String getBenBrDateStr() {
        if (StringUtils.isNotBlank(getBenBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getBenBrDate());
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

    public String getUnacpSdateStr() {
        if (StringUtils.isNotBlank(getUnacpSdate())) {
            return DateUtility.changeDateType(getUnacpSdate());
        }
        else {
            return "";
        }
    }

    public String getUnacpEdateStr() {
        if (StringUtils.isNotBlank(getUnacpEdate())) {
            return DateUtility.changeDateType(getUnacpEdate());
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

    public String getRecKind() {
        return recKind;
    }

    public void setRecKind(String recKind) {
        this.recKind = recKind;
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

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getUnacpDate() {
        return unacpDate;
    }

    public void setUnacpDate(String unacpDate) {
        this.unacpDate = unacpDate;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
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

    public BigDecimal getRealRecAmt() {
        return realRecAmt;
    }

    public void setRealRecAmt(BigDecimal realRecAmt) {
        this.realRecAmt = realRecAmt;
    }

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public String getUnacpSdate() {
        return unacpSdate;
    }

    public void setUnacpSdate(String unacpSdate) {
        this.unacpSdate = unacpSdate;
    }

    public String getUnacpEdate() {
        return unacpEdate;
    }

    public void setUnacpEdate(String unacpEdate) {
        this.unacpEdate = unacpEdate;
    }

    public String getPagePayKind() {
        return pagePayKind;
    }

    public void setPagePayKind(String pagePayKind) {
        this.pagePayKind = pagePayKind;
    }

}
