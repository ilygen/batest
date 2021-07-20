package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;

/**
 * case for 批次程式查詢作業
 * 
 * @author KIYOMI
 */
public class QryProcedureCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private int rowNum;
    private String procedureName; // 程式名稱
    private String status; // 處理狀態
    private String procType; // 處理類型(1:出媒體 2:批次月試核 3:第一次批次月核定 4:第二次批次月核定 )
    private String baJobId; // 資料列編號(JOBID)
    private String procBegTime;
    private String procEndTime;
    private String flagMsg;

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBaJobId() {
        return baJobId;
    }

    public void setBaJobId(String baJobId) {
        this.baJobId = baJobId;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public String getProcType() {
        return procType;
    }

    public void setProcType(String procType) {
        this.procType = procType;
    }

    public String getProcBegTime() {
        return procBegTime;
    }

    public void setProcBegTime(String procBegTime) {
        this.procBegTime = procBegTime;
    }

    public String getProcEndTime() {
        return procEndTime;
    }

    public void setProcEndTime(String procEndTime) {
        this.procEndTime = procEndTime;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

}
