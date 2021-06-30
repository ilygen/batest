package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;

/**
 * case for 媒體檔產製進度明細
 * 
 * @author Zehua
 */
public class MediaOnlineProgressDtlCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private int rowNum;
    private String flag; // 批次作業回傳代碼
    private String flagMsg; // 批次作業回傳訊息
    private String flagTime; // 批次作業處理時間

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
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
