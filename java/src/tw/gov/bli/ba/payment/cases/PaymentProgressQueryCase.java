package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentProgressQueryCase  implements Serializable {
	private String prtDate;
	private String paymentNo;
	private String caseNo;
	private String mApno;
	private BigDecimal payTotAmt;
	private BigDecimal nowStage;
	private String idn;
	private String paymentName;
	private String sequenceNo;
	private String paymentDate;
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
	public String getmApno() {
		return mApno;
	}
	public void setmApno(String mApno) {
		this.mApno = mApno;
	}
	public BigDecimal getPayTotAmt() {
		return payTotAmt;
	}
	public void setPayTotAmt(BigDecimal payTotAmt) {
		this.payTotAmt = payTotAmt;
	}
	public BigDecimal getNowStage() {
		return nowStage;
	}
	public void setNowStage(BigDecimal nowStage) {
		this.nowStage = nowStage;
	}
	public String getIdn() {
		return idn;
	}
	public void setIdn(String idn) {
		this.idn = idn;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
}
