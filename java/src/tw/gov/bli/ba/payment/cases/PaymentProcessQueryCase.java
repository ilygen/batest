package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentProcessQueryCase implements Serializable{
	private String evtName; //事故者姓名
	private String evtIdnno;//事故者身分證號
	private String prtDate;//列印日期
	private String paymentNo;//繳款單號
	private String caseNo;//移送案號
	private String apno;//受理編號
	private BigDecimal payTotAmt;//應繳總額
	public String getEvtName() {
		return evtName;
	}
	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}
	public String getEvtIdnno() {
		return evtIdnno;
	}
	public void setEvtIdnno(String evtIdnno) {
		this.evtIdnno = evtIdnno;
	}
	public String getPrtDate() {
		return prtDate;
	}
	public void setPrtDate(String prtDate) {
		this.prtDate = prtDate;
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
	public String getApno() {
		return apno;
	}
	public void setApno(String apno) {
		this.apno = apno;
	}
	public BigDecimal getPayTotAmt() {
		return payTotAmt;
	}
	public void setPayTotAmt(BigDecimal payTotAmt) {
		this.payTotAmt = payTotAmt;
	}
	
	
}
