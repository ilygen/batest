package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;

/**
 * Case for 勞保失能年金給付受理編審清單 - 另案扣減資料
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01DeductDataCase implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;

    private String rxfName;// 受款人姓名
    private String rxfApNo;// 受理編號
    private String payYm;// 給付年月
    private BigDecimal amtTot;// 應收總額
    private BigDecimal subAmt;// 未收餘額
    private BigDecimal rbAmt;// 本次收回金額
    private String prSt;// 處理狀況
    private String apNo; // 受理編號

    /**
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
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

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public BigDecimal getAmtTot() {
        return amtTot;
    }

    public void setAmtTot(BigDecimal amtTot) {
        this.amtTot = amtTot;
    }

    public BigDecimal getSubAmt() {
        return subAmt;
    }

    public void setSubAmt(BigDecimal subAmt) {
        this.subAmt = subAmt;
    }

    public BigDecimal getRbAmt() {
        return rbAmt;
    }

    public void setRbAmt(BigDecimal rbAmt) {
        this.rbAmt = rbAmt;
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
