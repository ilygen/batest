package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;
/**
 * 繳款單明細檔 (<code>BAPAYMENT</code>)
 * 
 */
@Table("BAPAYMENTDTL")
public class Bapaymentdtl implements Serializable{
	@PkeyField("PAYMENTNO")
	private String paymentNo;//繳款單號
	@LogField("APNO")
	private String apno;//受理編號
	@LogField("SEQNO")
	private String seqNo;//受款人序
	@LogField("PAYMENTNAME")
	private String paymentName;//姓名
	@LogField("ISSUYM")
	private String issuYm;//核定年月
	@LogField("PAYYM")
	private String payYm;//給付年月
	@LogField("RECAMT")
	private BigDecimal recAmt;//應收金額 
	@LogField("RECREM")
	private BigDecimal recRem;//未收金額
	@LogField("PAYAMT")
	private BigDecimal payAmt;//應繳本金
	@LogField("MAPNOMK")
	private String mApnoMk;//主辦案註記(1:主辦案; 0:非主辦案)
	@LogField("WRITEOFFSEQNO ")
	private String writeoffSeqno;//銷帳序
	@LogField("PAYMENTSEX ")
	private String paymentSex;//收件人性別
	//BAPAYMENT
	private String prtDate;//列印日期
	private String crtTime;//建立時間
	private String updTime;//更新時間
	private String caseNo;//移送案號
	private String idn;//身分證號
	private BigDecimal payTotAmt;//應繳總額
	private String nlwkMk;
	private String adwkMk;
	private String baunacpdtlId;
	private String sts;
	private String unacpDate;
	private String benIds;
	private String payKind;
	private String benIdnNo;
	public String getNlwkMk() {
		return nlwkMk;
	}
	public void setNlwkMk(String nlwkMk) {
		this.nlwkMk = nlwkMk;
	}
	public String getAdwkMk() {
		return adwkMk;
	}
	public void setAdwkMk(String adwkMk) {
		this.adwkMk = adwkMk;
	}
	public String getBaunacpdtlId() {
		return baunacpdtlId;
	}
	public void setBaunacpdtlId(String baunacpdtlId) {
		this.baunacpdtlId = baunacpdtlId;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getUnacpDate() {
		return unacpDate;
	}
	public void setUnacpDate(String unacpDate) {
		this.unacpDate = unacpDate;
	}
	public String getBenIds() {
		return benIds;
	}
	public void setBenIds(String benIds) {
		this.benIds = benIds;
	}
	public String getBenIdnNo() {
		return benIdnNo;
	}
	public void setBenIdnNo(String benIdnNo) {
		this.benIdnNo = benIdnNo;
	}
	public String getPaymentNo() {
		return paymentNo;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
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
	public String getApno() {
		return apno;
	}
	public void setApno(String apno) {
		this.apno = apno;
	}
	public String getmApnoMk() {
		return mApnoMk;
	}
	public void setmApnoMk(String mApnoMk) {
		this.mApnoMk = mApnoMk;
	}
	public String getWriteoffSeqno() {
		return writeoffSeqno;
	}
	public void setWriteoffSeqno(String writeoffSeqno) {
		this.writeoffSeqno = writeoffSeqno;
	}
	public String getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public BigDecimal getPayTotAmt() {
		return payTotAmt;
	}
	public void setPayTotAmt(BigDecimal payTotAmt) {
		this.payTotAmt = payTotAmt;
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
	public String getPaymentSex() {
		return paymentSex;
	}
	public void setPaymentSex(String paymentSex) {
		this.paymentSex = paymentSex;
	}
	public String getPayKind() {
		return payKind;
	}
	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}
}
