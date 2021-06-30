package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentReprintdtlCase   implements Serializable{
	private String paymentNo;
	private String maintainDate;
	private String caseNo;
	private String mApno;
	private BigDecimal payAmt;
	private String prtDate;
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	public String getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(String maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getmApno() {
		return mApno;
	}
	public void setmApno(String mApno) {
		this.mApno = mApno;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public String getPrtDate() {
		return prtDate;
	}
	public void setPrtDate(String prtDate) {
		this.prtDate = prtDate;
	}
}
