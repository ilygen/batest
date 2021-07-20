package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 勞保年金統計檔 (<code>BANSF</code>)
 * 
 * @author Eddie
 */
@Table("BANSF")
public class Bansf implements Serializable {

	private String issuYm; // 核定年月
	private String payCnt; // 核付件數
	private String pAmts; // 核付金額
	private String payKind; // 給付別
	private String seqNo; // 序號
	private String mchkTyp; // 月核案件類別
	private String sex; // 事故者性別
	private String evtNationTpe;// 被保險人國籍別
	private String payNo; // 給付種類
	private String cipbFMk; // 外籍別(CIPB)
	private String evType; // 傷病分類
	private String ubType; // 單位類別
	private String maxPayAmt;
	private String minPayAmt;
	private String avgPayAmt;
	private String avgPWage;
	private String avgNitrm;
	private String avgAge;
	private String hinPays;
	private String groupAlias;
	private String cntRatio;
	private String adWkMk;
	private String firstPay;
	public String getGroupAlias() {
		return groupAlias;
	}

	public void setGroupAlias(String groupAlias) {
		this.groupAlias = groupAlias;
	}

	public String getHinPays() {
		return hinPays;
	}

	public void setHinPays(String hinPays) {
		this.hinPays = hinPays;
	}

	public String getAvgPayAmt() {
		return avgPayAmt;
	}

	public void setAvgPayAmt(String avgPayAmt) {
		this.avgPayAmt = avgPayAmt;
	}

	public String getAvgPWage() {
		return avgPWage;
	}

	public void setAvgPWage(String avgPWage) {
		this.avgPWage = avgPWage;
	}

	public String getAvgNitrm() {
		return avgNitrm;
	}

	public void setAvgNitrm(String avgNitrm) {
		this.avgNitrm = avgNitrm;
	}

	public String getAvgAge() {
		return avgAge;
	}

	public void setAvgAge(String avgAge) {
		this.avgAge = avgAge;
	}

	public String getMaxPayAmt() {
		return maxPayAmt;
	}

	public void setMaxPayAmt(String maxPayAmt) {
		this.maxPayAmt = maxPayAmt;
	}

	public String getMinPayAmt() {
		return minPayAmt;
	}

	public void setMinPayAmt(String minPayAmt) {
		this.minPayAmt = minPayAmt;
	}

	public String getUbType() {
		return ubType;
	}

	public void setUbType(String ubType) {
		this.ubType = ubType;
	}

	public String getIssuYm() {
		return issuYm;
	}

	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}
	public void setPayCnt(String payCnt) {
		this.payCnt = payCnt;
	}

	public String getpAmts() {
		return pAmts;
	}

	public void setpAmts(String pAmts) {
		this.pAmts = pAmts;
	}

	public String getPayKind() {
		return payKind;
	}

	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getMchkTyp() {
		return mchkTyp;
	}

	public void setMchkTyp(String mchkTyp) {
		this.mchkTyp = mchkTyp;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEvtNationTpe() {
		return evtNationTpe;
	}

	public void setEvtNationTpe(String evtNationTpe) {
		this.evtNationTpe = evtNationTpe;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getCipbFMk() {
		return cipbFMk;
	}

	public void setCipbFMk(String cipbFMk) {
		this.cipbFMk = cipbFMk;
	}

	public String getEvType() {
		return evType;
	}

	public void setEvType(String evType) {
		this.evType = evType;
	}

	public String getCntRatio() {
		return cntRatio;
	}

	public void setCntRatio(String cntRatio) {
		this.cntRatio = cntRatio;
	}	

	public String getAdWkMk() {
		return adWkMk;
	}

	public void setAdWkMk(String adWkMk) {
		this.adWkMk = adWkMk;
	}

	public String getFirstPay() {
		return firstPay;
	}

	public void setFirstPay(String firstPay) {
		this.firstPay = firstPay;
	}

	public String getPayCnt() {
		return payCnt;
	}

}

	