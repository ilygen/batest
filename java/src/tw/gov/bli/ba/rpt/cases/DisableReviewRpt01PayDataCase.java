package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單 - 給付資料
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01PayDataCase implements Serializable {
    
    private String payYm; // 給付年月
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal adjustAmt; // 物價調整金額
    private BigDecimal issuCalcAmt; // 調整前金額
    private BigDecimal recAmt; // 應收金額
    private BigDecimal supAmt; // 補發金額
    private BigDecimal otherAmt; // 事故者扣減總金額
    private BigDecimal aplpayAmt; // 實付總額
    private BigDecimal offsetAmt; //紓困金額
    private BigDecimal payRate; // 匯款匯費
    private BigDecimal payBanance; //沖抵金額
    
    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
        else
            return StringUtils.defaultString(payYm);
    }
    
    public DisableReviewRpt01PayDataCase() {

    }
    
    public String getPayYm() {
        return payYm;
    }
    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }
    public BigDecimal getIssueAmt() {
        return issueAmt;
    }
    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }
    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }
    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }
    
    public BigDecimal getIssuCalcAmt() {
        return issuCalcAmt;
    }
    public void setIssuCalcAmt(BigDecimal issuCalcAmt) {
        this.issuCalcAmt = issuCalcAmt;
    }    
    
    public BigDecimal getRecAmt() {
        return recAmt;
    }
    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }
    public BigDecimal getSupAmt() {
        return supAmt;
    }
    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }
    public BigDecimal getOtherAmt() {
        return otherAmt;
    }
    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }
    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }
    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

	public BigDecimal getPayBanance() {
		return payBanance;
	}

	public void setPayBanance(BigDecimal payBanance) {
		this.payBanance = payBanance;
	}

	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}
    
    

}
