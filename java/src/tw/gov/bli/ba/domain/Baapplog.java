package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.Table;

/**
 * 給付資料更正記錄檔
 * 
 * @author Rickychi
 */
@Table("BAAPPLOG")
public class Baapplog implements Serializable {
	private BigDecimal baappbaseId; // 資料列編號
	private String status; // 狀態
	private String updTime; // 異動時間
	private String updUser; // 異動人員
	private String upCol; // 異動項目
	private String bvalue; // 改前內容
	private String avalue; // 改後內容
	private String issuYm; // 核定年月
	private String payYm; // 給付年月
	// Fields not in BAAPPLOG
	// [
	private String apNo; // 受理編號
	private String seqNo; // 序號
	private String benIdnNo; // 受益人身分證號
	// ]
	// Fields not in BAAPPLOG

	public Baapplog() {

	}

	public BigDecimal getBaappbaseId() {
		return baappbaseId;
	}

	public void setBaappbaseId(BigDecimal baappbaseId) {
		this.baappbaseId = baappbaseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public String getUpCol() {
		return upCol;
	}

	public void setUpCol(String upCol) {
		this.upCol = upCol;
	}

	public String getBvalue() {
		return bvalue;
	}

	public void setBvalue(String bvalue) {
		this.bvalue = bvalue;
	}

	public String getAvalue() {
		return avalue;
	}

	public void setAvalue(String avalue) {
		this.avalue = avalue;
	}

	public String getApNo() {
		return apNo;
	}

	public void setApNo(String apNo) {
		this.apNo = apNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getBenIdnNo() {
		return benIdnNo;
	}

	public void setBenIdnNo(String benIdnNo) {
		this.benIdnNo = benIdnNo;
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

}
