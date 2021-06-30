package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 勞保老年年金給付受理編審清單 - 另案扣減資料 - 一次給付
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01DeductOnceDataCase implements Serializable {
    private BigDecimal rowNum; // 序
    private String rxfName; // 受款人姓名 - 應收
    private String rxfApNo; // 受理編號 - 應收
    private BigDecimal subAmt; // 應收未收餘額
    private String prSt; // 處理狀況
    private String apNo; // 受理編號 - 撥付

    public OldAgeReviewRpt01DeductOnceDataCase() {

    }

    public BigDecimal getRowNum() {
        return rowNum;
    }

    public void setRowNum(BigDecimal rowNum) {
        this.rowNum = rowNum;
    }

    public String getRxfName() {
        return rxfName;
    }

    public void setRxfName(String rxfName) {
        this.rxfName = rxfName;
    }

    public String getRxfApNo() {
        return rxfApNo;
    }

    public void setRxfApNo(String rxfApNo) {
        this.rxfApNo = rxfApNo;
    }

    public BigDecimal getSubAmt() {
        return subAmt;
    }

    public void setSubAmt(BigDecimal subAmt) {
        this.subAmt = subAmt;
    }

    public String getPrSt() {
        return prSt;
    }

    public void setPrSt(String prSt) {
        this.prSt = prSt;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

}
