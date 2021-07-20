package tw.gov.bli.ba.domain;

import tw.gov.bli.common.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 給付媒體上傳記錄檔 (<code>BAMFILEFTPRECORD</code>)
 * 
 * @author Eddie
 */
@Table("BAMFILEFTPRECORD")
public class Bamfileftprecord implements Serializable {

	private String mfileName;// 媒體檔案名稱
	private String mfileDate;// 媒體檔案日期
	private String chkDate;// 核定日期
	private BigDecimal ftpSeq;// 上傳次數
	private String ftpDateRecord;// 上傳日期記錄

	public String getMfileName() {
		return mfileName;
	}

	public void setMfileName(String mfileName) {
		this.mfileName = mfileName;
	}

	public String getMfileDate() {
		return mfileDate;
	}

	public void setMfileDate(String mfileDate) {
		this.mfileDate = mfileDate;
	}

	public String getChkDate() {
		return chkDate;
	}

	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}

	public BigDecimal getFtpSeq() {
		return ftpSeq;
	}

	public void setFtpSeq(BigDecimal ftpSeq) {
		this.ftpSeq = ftpSeq;
	}

	public String getFtpDateRecord() {
		return ftpDateRecord;
	}

	public void setFtpDateRecord(String ftpDateRecord) {
		this.ftpDateRecord = ftpDateRecord;
	}
}

	