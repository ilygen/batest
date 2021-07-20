package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

@Table("BABATCHJOB")
public class Babatchjob implements Serializable {
    @PkeyField("BAJOBID")
    private String baJobId; // 資料列編號
    @LogField("ISSUYM")
    private String issuYm; // 核定年月
    @LogField("CHKDATE")
    private String chkDate; // 核定日期
    @LogField("PAYCODE")
    private String payCode; // 給付別
    @LogField("PROCEMPNO")
    private String procEmpNo; // 執行作業人員員工編號
    @LogField("PROCDEPTID")
    private String procDeptId; // 執行作業人員單位代碼
    @LogField("PROCIP")
    private String procIp; // 執行作業人員IP
    @LogField("PROCBEGTIME")
    private String procBegTime; // 處裡起始日期時間
    @LogField("PROCENDTIME")
    private String procEndTime; // 核定文號處理結束日期時間
    @LogField("PROCTYPE")
    private String procType; // 處理類型
    @LogField("STATUS")
    private String status; // 處理狀態
    @LogField("PAYSEQNO")
    private String paySeqNo; // 本欄位為失能年金使用： 1、為失能年金35、38案作業 2、僅失能國並勞36案作業
    @LogField("FILENAME")
    private String fileName; // 批次作業產製檔案名稱
    @LogField("DTEFLAG")
    private String dteFlag; // 批次作業明細註記
    @LogField("PROCEDURENAME")
    private String procedureName; // 程式名稱
    @LogField("FLAGMSG")
    private String flagMsg;

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

    public String getProcEmpNo() {
        return procEmpNo;
    }

    public void setProcEmpNo(String procEmpNo) {
        this.procEmpNo = procEmpNo;
    }

    public String getProcDeptId() {
        return procDeptId;
    }

    public void setProcDeptId(String procDeptId) {
        this.procDeptId = procDeptId;
    }

    public String getProcIp() {
        return procIp;
    }

    public void setProcIp(String procIp) {
        this.procIp = procIp;
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

    public String getPaySeqNo() {
        return paySeqNo;
    }

    public void setPaySeqNo(String paySeqNo) {
        this.paySeqNo = paySeqNo;
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

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

}
