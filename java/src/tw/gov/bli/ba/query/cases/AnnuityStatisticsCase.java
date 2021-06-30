package tw.gov.bli.ba.query.cases;

import java.io.Serializable;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 年金統計查詢
 * 
 * @author Eddie
 */
public class AnnuityStatisticsCase implements Serializable {
	private String issuYm;
	private String payCnt;
	private String pAmts;
	private String maxPayAmt;
	private String minPayAmt;
	private String sex;
	private String ubType;
	private String evtNationTpe;
	private String hinPays;
	private String fMk;
	private String cipbFMk;
	private String evType;
	private String payKind;
	private String avgPayAmt;
	private String avgPWage;
	private String avgNitrm;
	private String avgAge;
	private String groupAlias;
	private String cntRatio;

	public String getGroupAlias() {
		return groupAlias;
	}

	public void setGroupAlias(String groupAlias) {
		this.groupAlias = groupAlias;
	}

	public String getCntRatio() {
		return cntRatio;
	}

	public void setCntRatio(String cntRatio) {
		this.cntRatio = cntRatio;
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

	public String getIssuYm() {
		return issuYm;
	}

	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}

	public String getPayCnt() {
		return payCnt;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUbType() {
		return ubType;
	}

	public void setUbType(String ubType) {
		this.ubType = ubType;
	}

	public String getEvtNationTpe() {
		return evtNationTpe;
	}

	public void setEvtNationTpe(String evtNationTpe) {
		this.evtNationTpe = evtNationTpe;
	}

	public String getHinPays() {
		return hinPays;
	}

	public void setHinPays(String hinPays) {
		this.hinPays = hinPays;
	}

	public String getfMk() {
		return fMk;
	}

	public void setfMk(String fMk) {
		this.fMk = fMk;
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

	public String getPayKind() {
		return payKind;
	}

	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}

	public String getSexStr() {
		if (sex.equals("1"))
			return ConstantKey.BAAPPBASE_SEX_STR_1;
		else if (sex.equals("2"))
			return ConstantKey.BAAPPBASE_SEX_STR_2;
		else
			return "";
	}

	public String getEvtNationTpeStr() {
		if (evtNationTpe.equals("1"))
			return ConstantKey.BAAPPBASE_NATIONCODE_STR_1;
		if (evtNationTpe.equals("2"))
			return ConstantKey.BAAPPBASE_NATIONCODE_STR_2;
		else
			return "";
	}

	public String getCipbFMkStr() {
		if (cipbFMk.equals("F"))
			return ConstantKey.CIPBFMK_F;
		if (cipbFMk.equals("Y"))
			return ConstantKey.CIPBFMK_Y;
		if (cipbFMk.equals("N"))
			return ConstantKey.CIPBFMK_N;
		if (cipbFMk.equals("1"))
			return ConstantKey.CIPBFMK_1;
		if (cipbFMk.equals("2"))
			return ConstantKey.CIPBFMK_2;
		if (cipbFMk.equals("3"))
			return ConstantKey.CIPBFMK_3;
		if (cipbFMk.equals("4"))
			return ConstantKey.CIPBFMK_4;
		if (cipbFMk.equals("5"))
			return ConstantKey.CIPBFMK_5;
		else
			return ConstantKey.CIPBFMK_NON;
	}

	public String getIssuYmStr() {
		return DateUtility.changeWestYearMonthType(issuYm);
	}

}
