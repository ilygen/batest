package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;
/**
 * 繳款單主檔 (<code>BAPAYMENT</code>)
 * 
 */
@Table("BAPAYMENT")
public class Bapayment implements Serializable{
	@PkeyField("PAYMENTNO")
	private String paymentNo;//繳款單號
	@LogField("CASENO")
	private String caseNo;//移送案號
	@LogField("CASETYPE")
	private String caseType;//繳款單類別
	@LogField("PAYMENTDATELINE")
	private String paymentDateLine;//繳款期限
	@LogField("PAYMENTZIP")
	private String paymentZip;//郵遞區號
	@LogField("PAYMENTADDR")
	private String paymentAddr;//地址
	@LogField("TOTAMTSTAGE")
	private BigDecimal totAmtStage;//本金期數
	@LogField("INTERESTSTAGE")
	private BigDecimal interestStage;//利息期數
	@LogField("STAGEAMT")
	private BigDecimal stageAmt;//每期攤還本金
	@LogField("TOTTRGAMT")
	private BigDecimal totTrgAmt;//利息標的金額
	@LogField("INTERESTTRYSTAGE")
	private BigDecimal interestTryStage;//利息試算期數
	@LogField("MONTHLYPAYAMT")
	private BigDecimal monthlyPayAmt;//利息每期本金
	@LogField("PRTDATE")
	private String prtDate;//列印日期
	@LogField("IDN")
	private String idn;//身分證號
	@LogField("PAYTOTAMT")
	private BigDecimal payTotAmt;//應繳總額
	@LogField("USEMK")
	private String useMk;//使用註記
	@LogField("CRTTIME")
	private String crtTime;//鍵入時間
	@LogField("CRTUSER")
	private String crtUser;//鍵入人員
	@LogField("UPDTIME")
	private String updTime;//更新時間
	@LogField("UPDUSER")
	private String updUser;//更新人員

	private BigDecimal nowStage;//期別	
	private String chkObj;//繳款期限類別
	//baappbase
	private String paymentName;
	//bapaymentdtl
	private String apno;
	
	
	public String getApno() {
		return apno;
	}
	public void setApno(String apno) {
		this.apno = apno;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getPaymentDateLine() {
		return paymentDateLine;
	}
	public void setPaymentDateLine(String paymentDateLine) {
		this.paymentDateLine = paymentDateLine;
	}
	public String getPaymentZip() {
		return paymentZip;
	}
	public void setPaymentZip(String paymentZip) {
		this.paymentZip = paymentZip;
	}
	public String getPaymentAddr() {
		return paymentAddr;
	}
	public void setPaymentAddr(String paymentAddr) {
		this.paymentAddr = paymentAddr;
	}
	
	public BigDecimal getStageAmt() {
		return stageAmt;
	}
	public void setStageAmt(BigDecimal stageAmt) {
		this.stageAmt = stageAmt;
	}
	public BigDecimal getTotTrgAmt() {
		return totTrgAmt;
	}
	public void setTotTrgAmt(BigDecimal totTrgAmt) {
		this.totTrgAmt = totTrgAmt;
	}
	
	public BigDecimal getMonthlyPayAmt() {
		return monthlyPayAmt;
	}
	public void setMonthlyPayAmt(BigDecimal monthlyPayAmt) {
		this.monthlyPayAmt = monthlyPayAmt;
	}
	public String getPrtDate() {
		return prtDate;
	}
	public void setPrtDate(String prtDate) {
		this.prtDate = prtDate;
	}
	public String getIdn() {
		return idn;
	}
	public void setIdn(String idn) {
		this.idn = idn;
	}
	public BigDecimal getPayTotAmt() {
		return payTotAmt;
	}
	public void setPayTotAmt(BigDecimal payTotAmt) {
		this.payTotAmt = payTotAmt;
	}
	public String getUseMk() {
		return useMk;
	}
	public void setUseMk(String useMk) {
		this.useMk = useMk;
	}
	public BigDecimal getTotAmtStage() {
		return totAmtStage;
	}
	public void setTotAmtStage(BigDecimal totAmtStage) {
		this.totAmtStage = totAmtStage;
	}
	public BigDecimal getInterestStage() {
		return interestStage;
	}
	public void setInterestStage(BigDecimal interestStage) {
		this.interestStage = interestStage;
	}
	public BigDecimal getInterestTryStage() {
		return interestTryStage;
	}
	public void setInterestTryStage(BigDecimal interestTryStage) {
		this.interestTryStage = interestTryStage;
	}
	public String getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}
	public String getCrtUser() {
		return crtUser;
	}
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getChkObj() {
		return chkObj;
	}
	public void setChkObj(String chkObj) {
		this.chkObj = chkObj;
	}
	public BigDecimal getNowStage() {
		return nowStage;
	}
	public void setNowStage(BigDecimal nowStage) {
		this.nowStage = nowStage;
	}
	
}
