package tw.gov.bli.ba.payment.cases;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Case for 繳款單web service 子資料
 * 
 * @author Zehua
 */
public class PaymentDetailCase implements Serializable{
	private String writeOffSeqno;//銷帳序
	private BigDecimal payAmt;//應繳金額
	private String baUnacpdtlId;//應收未收id
	private String apno;//受理編號
	private String seqNo;//受款人序
	private String issuYm;//核定年月
	private String payYm;//給付年月
	private String sts;//狀態
	private String unacpDate;//立帳日期
	private BigDecimal recAmt;//應繳金額
	private BigDecimal recRem;//未收金額
	private String benIds;//受益人社福識別碼
	private String benIdnNo;//受益人idn
	private String benName;	//受益人姓名
	private String nlWkMk;//普職註記
	private String adWkMk;//加職註記
	private String payKind;//給付種類
	public String getBenName() {
		return benName;
	}
	public void setBenName(String benName) {
		this.benName = benName;
	}
	public String getNlWkMk() {
		return nlWkMk;
	}
	public void setNlWkMk(String nlWkMk) {
		this.nlWkMk = nlWkMk;
	}
	public String getAdWkMk() {
		return adWkMk;
	}
	public void setAdWkMk(String adWkMk) {
		this.adWkMk = adWkMk;
	}
	public String getWriteOffSeqno() {
		return writeOffSeqno;
	}
	public void setWriteOffSeqno(String writeOffSeqno) {
		this.writeOffSeqno = writeOffSeqno;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public String getBaUnacpdtlId() {
		return baUnacpdtlId;
	}
	public void setBaUnacpdtlId(String baUnacpdtlId) {
		this.baUnacpdtlId = baUnacpdtlId;
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
	public String getPayKind() {
		return payKind;
	}
	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}
	
}
