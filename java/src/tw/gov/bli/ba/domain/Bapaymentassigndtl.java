package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;
/**
 * 繳款單分配明細檔 (<code>BAPAYMENTASSIGNDTL</code>)
 * 
 */
@Table("BAPAYMENTASSIGNDTL")
public class Bapaymentassigndtl implements Serializable{
	@PkeyField("PAYMENTNO")
	private String paymentNo;//繳款單號
	@LogField("NOWSTAGE")
	private BigDecimal nowStage;//期別
	@LogField("APNO")
	private String apno;//受理編號
	@LogField("ISSUYM")
	private String issuYm;//核定年月
	@LogField("PAYYM")
	private String payYm;//給付年月
	@LogField("PAYAMT")
	private BigDecimal payAmt;//應繳本金
	@LogField("PAYINTEREST")
	private BigDecimal payInterest;//本期繳交利息
	@LogField("EXECAMT")
	private BigDecimal execAmt;//執行費用
	@LogField("PAYMENTNODETAIL")
	private String paymentNoDetail;//分期繳款單號
	@LogField("PAYMENTDATELINE")
	private String paymentDateLine;//繳款期限
	@LogField("BARCODE1")
	private String barCode1;//條碼1
	@LogField("BARCODE2")
	private String barCode2;//條碼2
	
	@LogField("BARCODE3")
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
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
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
	public BigDecimal getNowStage() {
		return nowStage;
	}
	public void setNowStage(BigDecimal nowStage) {
		this.nowStage = nowStage;
	}
	public String getApno() {
		return apno;
	}
	public void setApno(String apno) {
		this.apno = apno;
	}
	public String getPaymentDateLine() {
		return paymentDateLine;
	}
	public void setPaymentDateLine(String paymentDateLine) {
		this.paymentDateLine = paymentDateLine;
	}
	
	
}
