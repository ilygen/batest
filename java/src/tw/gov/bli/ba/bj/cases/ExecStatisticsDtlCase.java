package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
/**
 * Case for 年金統計執行作業 資料狀態
 * @author TseHua
 *
 */
public class ExecStatisticsDtlCase implements Serializable{
	private static final long serialVersionUID = -6696496926130024930L;
	private String flag; // 批次作業回傳代碼
    private String flagMsg; // 批次作業回傳訊息
    private String flagTime; // 批次作業處理時間
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
