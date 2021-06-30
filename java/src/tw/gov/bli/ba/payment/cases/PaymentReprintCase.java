package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Case for 繳款單列印作業
 * 
 * @author Zehua
 */
public class PaymentReprintCase  implements Serializable{
	private String maintainDate;//維護日期
	private String paymentNo;//繳款單號
	private String caseNo;//移送案號
	private String mApno;//主辦案號
	private BigDecimal payTotAmt;//應繳總額
	private String paymentName;//繳款單姓名
	private String idn;//身分證號
	private String prtDate;//列印日期
	private int maxInterest;//繳款單分期數
	//webservice for reprint
	private String cltAppSeq;
	private String msgId;
	private String dte;
	private String seq;
	private String deptId;
	private String modifyMan;
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
	public String getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(String maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getIdn() {
		return idn;
	}
	public void setIdn(String idn) {
		this.idn = idn;
	}
	public String getPrtDate() {
		return prtDate;
	}
	public void setPrtDate(String prtDate) {
		this.prtDate = prtDate;
	}
	public int getMaxInterest() {
		return maxInterest;
	}
	public void setMaxInterest(int maxInterest) {
		this.maxInterest = maxInterest;
	}
	public String getCltAppSeq() {
		return cltAppSeq;
	}
	public void setCltAppSeq(String cltAppSeq) {
		this.cltAppSeq = cltAppSeq;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getDte() {
		return dte;
	}
	public void setDte(String dte) {
		this.dte = dte;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getModifyMan() {
		return modifyMan;
	}
	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}
}
