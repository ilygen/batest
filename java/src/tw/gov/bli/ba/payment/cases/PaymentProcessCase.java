package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Case for 繳款單維護作業
 * 
 * @author Zehua
 */
public class PaymentProcessCase implements Serializable{
	private String apno;//受理編號
	private String seqNo;//受款人序
	private String payYm;//給付年月
	private String issuYm;//核定年月
	private String mapnoMk;//主辦案註記:0:非主辦案 1:主辦案
	private String benName;//姓名
	private BigDecimal recAmt;//應收金額
	private BigDecimal recRem;//未收餘額
	private BigDecimal payAmt;//應繳本金
	private String payKind;//給付種類
	private String addr;//地址
	private String zipCode;//郵遞區號
	private String writeoffSeqNo;//銷帳序
	private String paymentSex;//收件人性別
	public String getPayYm() {
		return payYm;
	}
	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}
	public String getIssuYm() {
		return issuYm;
	}
	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}
	
	public String getApno() {
		return apno;
	}
	public void setApno(String apno) {
		this.apno = apno;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getBenName() {
		return benName;
	}
	public void setBenName(String benName) {
		this.benName = benName;
	}
	public BigDecimal getRecAmt() {
		return recAmt;
	}
	public void setRecAmt(BigDecimal recAmt) {
		this.recAmt = recAmt;
	}
	public BigDecimal getRecRem() {
		return recRem;
	}
	public void setRecRem(BigDecimal recRem) {
		this.recRem = recRem;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPayKind() {
		return payKind;
	}
	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}
	public String getWriteoffSeqNo() {
		return writeoffSeqNo;
	}
	public void setWriteoffSeqNo(String writeoffSeqNo) {
		this.writeoffSeqNo = writeoffSeqNo;
	}
	public String getMapnoMk() {
		return mapnoMk;
	}
	public void setMapnoMk(String mapnoMk) {
		this.mapnoMk = mapnoMk;
	}
	public String getPaymentSex() {
		return paymentSex;
	}
	public void setPaymentSex(String paymentSex) {
		this.paymentSex = paymentSex;
	}
	
}
