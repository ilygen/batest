package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;


/**
 * Case for 勞保失能年金給付受理編審清單 - 受款人給付資料
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01BenPayDataCase implements Serializable{
    
    private String payYm; // 給付年月
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal otherAmt; // 扣減金額
    private BigDecimal payRate; // 匯款匯費
    private BigDecimal aplpayAmt; // 實付金額
    private BigDecimal badDebtAmt; // 呆帳金額
    private BigDecimal unitpayAmt; // 投保單位墊付金額
    private BigDecimal returnAmt; // 已歸墊金額
    private BigDecimal remainAmt; // 尚未歸墊金額
    private BigDecimal loanAmt; // 勞貸貸款金額
    private BigDecimal compenAmt; // 實際補償金額
    private BigDecimal lecomAmt; // 已代扣補償金額
    private BigDecimal recomAmt; // 未扣補償金金額
    private BigDecimal nitrmY; // 老年-投保年資(年-年金制);失能-勞保投保(年-年金制)
    private BigDecimal nitrmM; // 老年-投保年資(月-年金制);失能-勞保投保(月-年金制
    private BigDecimal aplPaySeniY; // 老年-實付年資(年);失能-實發年資(日)
    private BigDecimal aplPaySeniM; // 老年-實付年資(月);失能-實發年資(日)
    private BigDecimal oldRate; // 老年-展延/減額比率;失能-加計比率(加發眷屬補助比率)
    private BigDecimal oldaAmt; // 老年-第一式式金額;失能-勞保計算金額
    private BigDecimal oldbAmt; // 老年-第二式式金額;失能-勞保給付金額
    private BigDecimal oldAmt; // 老年-第一式式金額/第二式式金額(擇領)
    private BigDecimal qualCount; //眷屬符合人數
    private String payKind;
    private BigDecimal basicAmt; //38案 基本金額
    
    
    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
        else
            return "";
    }
    
    public BigDecimal getOldRateAmt() {
    		 BigDecimal oldbAmt = new BigDecimal(0);

    	     if (getOldbAmt() != null) {
    	          oldbAmt = getOldbAmt();
    	     }

    	     return oldbAmt;
    }
    
    public BigDecimal getOldRateAmt38() {
    	
    	oldbAmt  = (oldbAmt == null) ? new BigDecimal(0) : oldbAmt;
		oldRate  = (oldRate == null) ? new BigDecimal(0) : oldRate;
		basicAmt = (basicAmt == null) ? new BigDecimal(0) : basicAmt;
    	
    if(oldbAmt.intValue() >= basicAmt.intValue()){
		 return oldbAmt.multiply(oldRate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP);
	 }else{
		 return basicAmt.multiply(oldRate.divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP);
	 }
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
    public BigDecimal getOtherAmt() {
        return otherAmt;
    }
    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }
    public BigDecimal getPayRate() {
        return payRate;
    }
    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }
    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }
    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }
    public BigDecimal getBadDebtAmt() {
        return badDebtAmt;
    }
    public void setBadDebtAmt(BigDecimal badDebtAmt) {
        this.badDebtAmt = badDebtAmt;
    }
    public BigDecimal getUnitpayAmt() {
        return unitpayAmt;
    }
    public void setUnitpayAmt(BigDecimal unitpayAmt) {
        this.unitpayAmt = unitpayAmt;
    }
    public BigDecimal getReturnAmt() {
        return returnAmt;
    }
    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }
    public BigDecimal getRemainAmt() {
        return remainAmt;
    }
    public void setRemainAmt(BigDecimal remainAmt) {
        this.remainAmt = remainAmt;
    }
    public BigDecimal getLoanAmt() {
        return loanAmt;
    }
    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }
    public BigDecimal getCompenAmt() {
        return compenAmt;
    }
    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }
    public BigDecimal getLecomAmt() {
        return lecomAmt;
    }
    public void setLecomAmt(BigDecimal lecomAmt) {
        this.lecomAmt = lecomAmt;
    }
    public BigDecimal getRecomAmt() {
        return recomAmt;
    }
    public void setRecomAmt(BigDecimal recomAmt) {
        this.recomAmt = recomAmt;
    }

    public BigDecimal getNitrmY() {
        return nitrmY;
    }

    public void setNitrmY(BigDecimal nitrmY) {
        this.nitrmY = nitrmY;
    }

    public BigDecimal getNitrmM() {
        return nitrmM;
    }

    public void setNitrmM(BigDecimal nitrmM) {
        this.nitrmM = nitrmM;
    }

    public BigDecimal getAplPaySeniY() {
        return aplPaySeniY;
    }

    public void setAplPaySeniY(BigDecimal aplPaySeniY) {
        this.aplPaySeniY = aplPaySeniY;
    }

    public BigDecimal getAplPaySeniM() {
        return aplPaySeniM;
    }

    public void setAplPaySeniM(BigDecimal aplPaySeniM) {
        this.aplPaySeniM = aplPaySeniM;
    }

    public BigDecimal getOldRate() {
        return oldRate;
    }

    public void setOldRate(BigDecimal oldRate) {
        this.oldRate = oldRate;
    }

    public BigDecimal getOldaAmt() {
        return oldaAmt;
    }

    public void setOldaAmt(BigDecimal oldaAmt) {
        this.oldaAmt = oldaAmt;
    }

    public BigDecimal getOldbAmt() {
        return oldbAmt;
    }

    public void setOldbAmt(BigDecimal oldbAmt) {
        this.oldbAmt = oldbAmt;
    }

    public BigDecimal getOldAmt() {
        return oldAmt;
    }

    public void setOldAmt(BigDecimal oldAmt) {
        this.oldAmt = oldAmt;
    }

    public BigDecimal getQualCount() {
        return qualCount;
    }

    public void setQualCount(BigDecimal qualCount) {
        this.qualCount = qualCount;
    }

	public String getPayKind() {
		return payKind;
	}

	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}

	public BigDecimal getBasicAmt() {
		return basicAmt;
	}

	public void setBasicAmt(BigDecimal basicAmt) {
		this.basicAmt = basicAmt;
	}

    

}
