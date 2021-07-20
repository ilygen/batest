package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentWebserviceDtlCase implements Serializable{
	private int divSeq;//分割序號
	private String offKind;//銷號類別
	private String tempHandleNo;//提繳受理編號	
	private int divAmout;//分割金額
	private String tempChkMemo;//暫收查明移至摘要
	private String gvSeq;//受款人序
	private String nlwkMk;//普職註記
	private String cmmk;//現醫註記
	private String adwkMk;//一般加職註記
	private String accpetIssueKind;//給付種類	
	
	public String getAccpetIssueKind() {
		return accpetIssueKind;
	}
	public void setAccpetIssueKind(String accpetIssueKind) {
		this.accpetIssueKind = accpetIssueKind;
	}
	public int getDivSeq() {
		return divSeq;
	}
	public void setDivSeq(int divSeq) {
		this.divSeq = divSeq;
	}
	public String getOffKind() {
		return offKind;
	}
	public void setOffKind(String offKind) {
		this.offKind = offKind;
	}
	public String getTempHandleNo() {
		return tempHandleNo;
	}
	public void setTempHandleNo(String tempHandleNo) {
		this.tempHandleNo = tempHandleNo;
	}
	public int getDivAmout() {
		return divAmout;
	}
	public void setDivAmout(int divAmout) {
		this.divAmout = divAmout;
	}
	public String getTempChkMemo() {
		return tempChkMemo;
	}
	public void setTempChkMemo(String tempChkMemo) {
		this.tempChkMemo = tempChkMemo;
	}
	public String getGvSeq() {
		return gvSeq;
	}
	public void setGvSeq(String gvSeq) {
		this.gvSeq = gvSeq;
	}
	public String getNlwkMk() {
		return nlwkMk;
	}
	public void setNlwkMk(String nlwkMk) {
		this.nlwkMk = nlwkMk;
	}
	public String getCmmk() {
		return cmmk;
	}
	public void setCmmk(String cmmk) {
		this.cmmk = cmmk;
	}
	public String getAdwkMk() {
		return adwkMk;
	}
	public void setAdwkMk(String adwkMk) {
		this.adwkMk = adwkMk;
	}
	
}
