package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 複檢費用資料
 * 
 * @author Rickychi
 */
public class PaymentQueryReFeesMasterDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序
    private String apNo; // 受理編號
    private String inreDate; // 通知複檢日期
    private String recosDate; // 複檢費用申請日期
    private BigDecimal reFees; // 複檢費用
    private BigDecimal nonreFees; // 非複檢必須費用
    private BigDecimal reAmtPay; // 複檢實付金額
    private String payDate; // 核付日期

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return getApNo();
    }

    public String getReApNoStrDisplay() {
        if (StringUtils.isNotBlank(getReApNo()) && getReApNo().length() == ConstantKey.APNO_LENGTH)
            return getReApNo().substring(0, 1) + "-" + getReApNo().substring(1, 2) + "-" + getReApNo().substring(2, 7) + "-" + getReApNo().substring(7, 12);
        else
            return getReApNo();
    }

    public String getInreDateStr() {
        if (StringUtils.isNotBlank(getInreDate())) {
            return DateUtility.changeDateType(getInreDate());
        }
        else {
            return getInreDate();
        }
    }

    public String getRecosDateStr() {
        if (StringUtils.isNotBlank(getRecosDate())) {
            return DateUtility.changeDateType(getRecosDate());
        }
        else {
            return getRecosDate();
        }
    }

    public String getPayDateStr() {
        if (StringUtils.isNotBlank(getPayDate())) {
            return DateUtility.changeDateType(getPayDate());
        }
        else {
            return getPayDate();
        }
    }

    // ]
    public String getReApNo() {
        return reApNo;
    }

    public void setReApNo(String reApNo) {
        this.reApNo = reApNo;
    }

    public BigDecimal getApSeq() {
        return apSeq;
    }

    public void setApSeq(BigDecimal apSeq) {
        this.apSeq = apSeq;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getInreDate() {
        return inreDate;
    }

    public void setInreDate(String inreDate) {
        this.inreDate = inreDate;
    }

    public String getRecosDate() {
        return recosDate;
    }

    public void setRecosDate(String recosDate) {
        this.recosDate = recosDate;
    }

    public BigDecimal getReFees() {
        return reFees;
    }

    public void setReFees(BigDecimal reFees) {
        this.reFees = reFees;
    }

    public BigDecimal getNonreFees() {
        return nonreFees;
    }

    public void setNonreFees(BigDecimal nonreFees) {
        this.nonreFees = nonreFees;
    }

    public BigDecimal getReAmtPay() {
        return reAmtPay;
    }

    public void setReAmtPay(BigDecimal reAmtPay) {
        this.reAmtPay = reAmtPay;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

}
