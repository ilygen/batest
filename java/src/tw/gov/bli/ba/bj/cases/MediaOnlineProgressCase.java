package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;

/**
 * case for 媒體檔產製進度查詢
 * 
 * @author Zehua
 */
public class MediaOnlineProgressCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private int rowNum;
    private String issuYm; // 核定年月
    private String chkDate; // 核定日期
    private String payCode; // 給付別
    private String status; // 處理狀態
    private String procType; // 處理類型(1:出媒體 2:批次月試核 3:第一次批次月核定 4:第二次批次月核定 )
    private String baJobId; // 資料列編號(JOBID)
    private String payCodeStr; // 給付別 : K:失能 L:老年 S:遺屬

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

    public String getPayCodeStr() {
        return payCodeStr;
    }

    public void setPayCodeStr(String payCodeStr) {
        this.payCodeStr = payCodeStr;
    }

}
