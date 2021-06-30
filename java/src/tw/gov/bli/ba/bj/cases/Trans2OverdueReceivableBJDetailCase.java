package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * case for 轉催收作業 Detail
 * 
 * @author Rickychi
 */
public class Trans2OverdueReceivableBJDetailCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private BigDecimal baunacprecId;// 應收記錄編號
    private String apNo;// 受理編號
    private String evtName;// 立帳對象
    private String evtIdnNo;// 身分證號
    private String unacpDate;// 立帳日期
    private BigDecimal recAmt;// 應收金額
    private BigDecimal woAmt;// 已收金額
    private BigDecimal recRem;// 未收金額

    // 頁面顯示轉換
    // [
    public String getApNoStr() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12) + "-" + getApNo().substring(12, 13);
    }

    public String getUnacpDateStr() {
        if (StringUtils.isNotBlank(getUnacpDate())) {
            return DateUtility.changeDateType(getUnacpDate());
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

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getUnacpDate() {
        return unacpDate;
    }

    public void setUnacpDate(String unacpDate) {
        this.unacpDate = unacpDate;
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

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

}
