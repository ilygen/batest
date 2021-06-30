package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

public class MonthBatchKCase implements Serializable {
	private String baJobId; //資料列編號(JOBID)
	private String issuYm; //核定年月
	private String chkDate; //核定日期
	private String payCode; //給付別
	private String procType; //處理類型
	private String status; //處理狀態
	private String procBegTime; //處裡起始日期時間
	private String fileName; //批次作業產製檔案名稱
	private String paySeqNo; // 本欄位為失能年金使用：  1、為失能年金35、38案作業  2、僅失能國並勞36案作業
	private String dteFlag; //批次作業明細註記  
	
	public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm())) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return "";
        }
    }
	
	public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate())) {
            return DateUtility.changeDateType(getChkDate());
        }
        else {
            return "";
        }
    }
	
	public String getBaJobId() {
		return baJobId;
	}
	public void setBaJobId(String baJobId) {
		this.baJobId = baJobId;
	}
	public String getIssuYm() {
		return issuYm;
	}
	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}
	public String getChkDate() {
		return chkDate;
	}
	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcBegTime() {
		return procBegTime;
	}
	public void setProcBegTime(String procBegTime) {
		this.procBegTime = procBegTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDteFlag() {
		return dteFlag;
	}
	public void setDteFlag(String dteFlag) {
		this.dteFlag = dteFlag;
	}

	public String getPaySeqNo() {
		return paySeqNo;
	}

	public void setPaySeqNo(String paySeqNo) {
		this.paySeqNo = paySeqNo;
	}
	
}
