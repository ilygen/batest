package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentStageDtlCase implements Serializable{
	private int nowStage;//期別
	private String nowStagForShow;//期別bapm0d015m用
	private BigDecimal iRate;//利率
	private BigDecimal rePayAmt;//期末還款金額
	private BigDecimal accAmt;//期初本金餘額
	private BigDecimal accAmtEnd;//期末本金餘額
	private String interestBegDate;//利息起算日
	private String interestEndDate;//利息結算日
	private BigDecimal tryInterest;//試算利息
	private BigDecimal adjInterest;//調整利息
	private String apno;//受理編號
	private String issuYm;//核定年月
	private String payYm;//給付年月
	private BigDecimal payAmt;//應繳本金
	private BigDecimal payInterest;//本期繳交利息
	private BigDecimal execAmt;//執行費用
	private String paymentNoDetail;//分期繳款單號
	private int dateBetween;//天數
	private String paymentDateLine;//繳款期限
	private String rateMsg;//利率顯示訊息
	private String compDate;//補印日期
	private String mflag;//用來記錄執行費用可修改的期別(僅有本金最後一期與利息期別可以修改 )1:可修改0:不可修改
	private String barCode1;//條碼1
	private String barCode2;//條碼2
	private String barCode3;//條碼3
	
	public String getBarCode1() {
		return barCode1;
	}
	public void setBarCode1(String barCode1) {
		this.barCode1 = barCode1;
	}
	public String getBarCode2() {
		return barCode2;
	}
	public void setBarCode2(String barCode2) {
		this.barCode2 = barCode2;
	}
	public String getBarCode3() {
		return barCode3;
	}
	public void setBarCode3(String barCode3) {
		this.barCode3 = barCode3;
	}
	public int getNowStage() {
		return nowStage;
	}
	public void setNowStage(int nowStage) {
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
	public String getApno() {
		return apno;
	}
	public void setApno(String apno) {
		this.apno = apno;
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
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public BigDecimal getPayInterest() {
		return payInterest;
	}
	public void setPayInterest(BigDecimal payInterest) {
		this.payInterest = payInterest;
	}
	public BigDecimal getExecAmt() {
		return execAmt;
	}
	public void setExecAmt(BigDecimal execAmt) {
		this.execAmt = execAmt;
	}
	public String getPaymentNoDetail() {
		return paymentNoDetail;
	}
	public void setPaymentNoDetail(String paymentNoDetail) {
		this.paymentNoDetail = paymentNoDetail;
	}
	public int getDateBetween() {
		return dateBetween;
	}
	public void setDateBetween(int dateBetween) {
		this.dateBetween = dateBetween;
	}
	public String getRateMsg() {
		return rateMsg;
	}
	public void setRateMsg(String rateMsg) {
		this.rateMsg = rateMsg;
	}
	public String getPaymentDateLine() {
		return paymentDateLine;
	}
	public void setPaymentDateLine(String paymentDateLine) {
		this.paymentDateLine = paymentDateLine;
	}
	public String getNowStagForShow() {
		return nowStagForShow;
	}
	public void setNowStagForShow(String nowStagForShow) {
		this.nowStagForShow = nowStagForShow;
	}
	public String getCompDate() {
		return compDate;
	}
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}
	
	public String getMflag() {
		return mflag;
	}
	public void setMflag(String mflag) {
		this.mflag = mflag;
	}
	public BigDecimal getAccAmtEnd() {
		return accAmtEnd;
	}
	public void setAccAmtEnd(BigDecimal accAmtEnd) {
		this.accAmtEnd = accAmtEnd;
	}
	
}
