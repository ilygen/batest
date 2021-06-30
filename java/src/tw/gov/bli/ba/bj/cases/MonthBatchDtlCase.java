package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

public class MonthBatchDtlCase implements Serializable {
	private BigDecimal baJobDtlId; //明細檔編號
	private String baJobId; //資料列編號(JOBID)
	private String flag; //批次作業回傳代碼  0:成功 1:失敗
	private String flagMsg; //批次作業回傳訊息 各步驟依照完成階段寫入階段訊息
	private String flagTime; //批次作業處理時間 各步驟依照完成階段寫入階段完成時間
	
	public BigDecimal getBaJobDtlId() {
		return baJobDtlId;
	}
	public void setBaJobDtlId(BigDecimal baJobDtlId) {
		this.baJobDtlId = baJobDtlId;
	}
	public String getBaJobId() {
		return baJobId;
	}
	public void setBaJobId(String baJobId) {
		this.baJobId = baJobId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlagMsg() {
		return flagMsg;
	}
	public void setFlagMsg(String flagMsg) {
		this.flagMsg = flagMsg;
	}
	public String getFlagTime() {
		return flagTime;
	}
	public void setFlagTime(String flagTime) {
		this.flagTime = flagTime;
	}

   
}
