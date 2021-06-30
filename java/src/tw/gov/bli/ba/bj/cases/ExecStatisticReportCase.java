package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExecStatisticReportCase implements Serializable{
	private static final long serialVersionUID = -5395693347057560175L;
	private String issuYm;//核定年月
	private String payNo;//給付種類
	private String firstPay;//首發註記
	private String evType;//傷病分類
	private String adWkMk;//已領老年註記
	private BigDecimal payCnt;//核付件數
	private BigDecimal pamts;//核付金額
	
	public String getIssuYm() {
		return issuYm;
	}
	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getFirstPay() {
		return firstPay;
	}
	public void setFirstPay(String firstPay) {
		this.firstPay = firstPay;
	}
	public String getEvType() {
		return evType;
	}
	public void setEvType(String evType) {
		this.evType = evType;
	}
	public String getAdWkMk() {
		return adWkMk;
	}
	public void setAdWkMk(String adWkMk) {
		this.adWkMk = adWkMk;
	}
	public BigDecimal getPayCnt() {
		return payCnt;
	}
	public void setPayCnt(BigDecimal payCnt) {
		this.payCnt = payCnt;
	}
	public BigDecimal getPamts() {
		return pamts;
	}
	public void setPamts(BigDecimal pamts) {
		this.pamts = pamts;
	}

}