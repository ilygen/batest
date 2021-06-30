package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;
/**
 * 繳款單利息明細檔(<code>BAPAYMENT</code>)
 * 
 */
@Table("BAPAYMENTSTAGEDTL")
public class Bapaymentstagedtl implements Serializable{
	@PkeyField("PAYMENTNO")
	private String paymentNo;//繳款單號
	@PkeyField("NOWSTAGE")
	private BigDecimal nowStage;//期別
	@PkeyField("IRATE")
	private BigDecimal iRate;//利率
	@LogField("REPAYAMT")
	private BigDecimal rePayAmt;//還款金額
	@LogField("ACCAMT")
	private BigDecimal accAmt;//剩餘本金
	@LogField("INTERESTBEGDATE")
	private String interestBegDate ;//利息起算日
	@LogField("INTERESTENDDATE")
	private String interestEndDate;//利息結算日
	@LogField("TRYINTEREST")
	private BigDecimal tryInterest;//試算利息
	@LogField("ADJINTEREST")
	private BigDecimal adjInterest;//調整利息
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public BigDecimal getNowStage() {
		return nowStage;
	}
	public void setNowStage(BigDecimal nowStage) {
		this.nowStage = nowStage;
	}
	public BigDecimal getiRate() {
		return iRate;
	}
	public void setiRate(BigDecimal iRate) {
		this.iRate = iRate;
	}
	public BigDecimal getRePayAmt() {
		return rePayAmt;
	}
	public void setRePayAmt(BigDecimal rePayAmt) {
		this.rePayAmt = rePayAmt;
	}
	public BigDecimal getAccAmt() {
		return accAmt;
	}
	public void setAccAmt(BigDecimal accAmt) {
		this.accAmt = accAmt;
	}
	public String getInterestBegDate() {
		return interestBegDate;
	}
	public void setInterestBegDate(String interestBegDate) {
		this.interestBegDate = interestBegDate;
	}
	public String getInterestEndDate() {
		return interestEndDate;
	}
	public void setInterestEndDate(String interestEndDate) {
		this.interestEndDate = interestEndDate;
	}
	public BigDecimal getTryInterest() {
		return tryInterest;
	}
	public void setTryInterest(BigDecimal tryInterest) {
		this.tryInterest = tryInterest;
	}
	public BigDecimal getAdjInterest() {
		return adjInterest;
	}
	public void setAdjInterest(BigDecimal adjInterest) {
		this.adjInterest = adjInterest;
	}
}
