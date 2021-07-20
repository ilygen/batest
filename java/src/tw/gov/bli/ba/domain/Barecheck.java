package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 重新查核失能程度檔 (<code>BARECHECK</code>)
 *
 * @author frank
 */
public class Barecheck implements Serializable {
	private String apNo; //受理編號
	private String seqNo; //受款人序
    private String reChkYm;     // 重新查核失能程度年月
    private String isreChk;     // 是否複檢
    private String reChkStatus; // 重新查核狀態
    private String reChkResult; // 重新查核結果
    private String comReChkYm;  // 完成重新查核年月
    private String updUser;
    private String updTime;
    
	public String getReChkYm() {
		return reChkYm;
	}
	public void setReChkYm(String reChkYm) {
		this.reChkYm = reChkYm;
	}
	public String getIsreChk() {
		return isreChk;
	}
	public void setIsreChk(String isreChk) {
		this.isreChk = isreChk;
	}
	public String getReChkStatus() {
		return reChkStatus;
	}
	public void setReChkStatus(String reChkStatus) {
		this.reChkStatus = reChkStatus;
	}
	public String getReChkResult() {
		return reChkResult;
	}
	public void setReChkResult(String reChkResult) {
		this.reChkResult = reChkResult;
	}
	public String getComReChkYm() {
		return comReChkYm;
	}
	public void setComReChkYm(String comReChkYm) {
		this.comReChkYm = comReChkYm;
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
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	public String getUpdTime() {
		return updTime;
	}
	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
	
}
