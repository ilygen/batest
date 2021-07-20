package tw.gov.bli.ba.payment.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class PaymentReprintForm extends BaseValidatorForm{
	private String idn;
	private String paymentNo;
	private String paymentName;//
	private String caseNo;
	private String method;//
	private String idForConfirm;
	private String printDt;
	private String idForConfirmDtl;
	private String sequenceNo;
	private String paymentDate;
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
	    return errors;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

	}
	public String getIdn() {
		return idn;
	}
	public void setIdn(String idn) {
		this.idn = idn;
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
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getIdForConfirm() {
		return idForConfirm;
	}
	public void setIdForConfirm(String idForConfirm) {
		this.idForConfirm = idForConfirm;
	}
	public String getPrintDt() {
		return printDt;
	}
	public void setPrintDt(String printDt) {
		this.printDt = printDt;
	}
	public String getIdForConfirmDtl() {
		return idForConfirmDtl;
	}
	public void setIdForConfirmDtl(String idForConfirmDtl) {
		this.idForConfirmDtl = idForConfirmDtl;
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
