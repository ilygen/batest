package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 月核案件檢核檔 (<code>BARVCASE</code>)
 *
 * @author Noctis
 */

public class Barvcase implements Serializable {
	private String apNo; //受理編號
	private String mchkTyp; //月核案件類別
	private String payYm; //給付年月
	private BigDecimal oldRate; //展延 減額比率
	private BigDecimal befIssueAmt; //總核定金額
	private String payKind; //給付種類
	private String benName; //受益人姓名
	private BigDecimal lastIssueAmt; //前次核定金額
	
	public String getApNo() {
		return apNo;
	}
	public void setApNo(String apNo) {
		this.apNo = apNo;
	}
	public String getMchkTyp() {
		return mchkTyp;
	}
	public void setMchkTyp(String mchkTyp) {
		this.mchkTyp = mchkTyp;
	}
	public String getPayYm() {
		return payYm;
	}
	public void setPayYm(String payYm) {
		this.payYm = payYm;
	}
	public BigDecimal getOldRate() {
		return oldRate;
	}
	public void setOldRate(BigDecimal oldRate) {
		this.oldRate = oldRate;
	}
	public BigDecimal getBefIssueAmt() {
		return befIssueAmt;
	}
	public void setBefIssueAmt(BigDecimal befIssueAmt) {
		this.befIssueAmt = befIssueAmt;
	}
	public String getPayKind() {
		return payKind;
	}
	public void setPayKind(String payKind) {
		this.payKind = payKind;
	}
	public String getBenName() {
		return benName;
	}
	public void setBenName(String benName) {
		this.benName = benName;
	}
	public BigDecimal getLastIssueAmt() {
		return lastIssueAmt;
	}
	public void setLastIssueAmt(BigDecimal lastIssueAmt) {
		this.lastIssueAmt = lastIssueAmt;
	}

}
