package tw.gov.bli.ba.bj.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

import java.math.BigDecimal;

public class SurvivorMediaBatchForm extends BaseValidatorForm {
	private String method;
	private String issuTyp;//核付處理類別
	private String chkDate; //核定日期
	private String issuYm;//核定年月
	private String mfileName; //媒體檔案名稱
	private String baJobId;//資料列編號(JOBID)
	private BigDecimal ftpSeq;// 上傳次數
	private String mfileDate;// 媒體檔案日期

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getIssuTyp() {
		return issuTyp;
	}
	public void setIssuTyp(String issuTyp) {
		this.issuTyp = issuTyp;
	}
	public String getChkDate() {
		return chkDate;
	}
	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}
	public String getIssuYm() {
		return issuYm;
	}
	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}
	public String getMfileName() {
		return mfileName;
	}
	public void setMfileName(String mfileName) {
		this.mfileName = mfileName;
	}
	public String getBaJobId() {
		return baJobId;
	}
	public void setBaJobId(String baJobId) {
		this.baJobId = baJobId;
	}

	public BigDecimal getFtpSeq() {
		return ftpSeq;
	}

	public void setFtpSeq(BigDecimal ftpSeq) {
		this.ftpSeq = ftpSeq;
	}

	public String getMfileDate() {
		return mfileDate;
	}

	public void setMfileDate(String mfileDate) {
		this.mfileDate = mfileDate;
	}
}
