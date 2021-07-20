package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 批次作業記錄檔
 * 
 * @author swim
 */
@Table("BABATCHREC")
public class Babatchrec implements Serializable {
    @PkeyField("BABATCHRECID")
    private BigDecimal baBatchRecId; // 資料列編號
    
    private String batchTyp; // 批次處理類型
    private String sunit; // 發件單位
    private String taTyp; // 出帳類別
    private String payDate; // 出帳日期
    private BigDecimal dataCount; // 筆數
    private String stTime; // 開始時間
    private String endTime; // 結束時間
    private String executeTime; // 執行/耗費時間
    private String fileName; // 檔案名稱
    private String upTyp; // 資料來源類別
    private String upOrgan; // 資料來源
    private String upOrganId; // 資料來源單位ID
    private String upTime; // 檔案產生時間
    private BigDecimal amount; // 總金額
    private String procStat; // 處理狀態
    private String procFlag; // 回押註記
    private String procMemo; // 訊息說 明
    private String procUser; // 處理人員
    private String procTime; // 處理日期
    private String updTime; // 異動日期時間

    public Babatchrec() {

    }

    public BigDecimal getBaBatchRecId() {
        return baBatchRecId;
    }

    public void setBaBatchRecId(BigDecimal baBatchRecId) {
        this.baBatchRecId = baBatchRecId;
    }

    public String getBatchTyp() {
        return batchTyp;
    }

    public void setBatchTyp(String batchTyp) {
        this.batchTyp = batchTyp;
    }

    public String getSunit() {
        return sunit;
    }

    public void setSunit(String sunit) {
        this.sunit = sunit;
    }

    public String getTaTyp() {
        return taTyp;
    }

    public void setTaTyp(String taTyp) {
        this.taTyp = taTyp;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public String getStTime() {
        return stTime;
    }

    public void setStTime(String stTime) {
        this.stTime = stTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUpTyp() {
        return upTyp;
    }

    public void setUpTyp(String upTyp) {
        this.upTyp = upTyp;
    }

    public String getUpOrgan() {
        return upOrgan;
    }

    public void setUpOrgan(String upOrgan) {
        this.upOrgan = upOrgan;
    }

    public String getUpOrganId() {
        return upOrganId;
    }

    public void setUpOrganId(String upOrganId) {
        this.upOrganId = upOrganId;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getProcFlag() {
        return procFlag;
    }

    public void setProcFlag(String procFlag) {
        this.procFlag = procFlag;
    }

    public String getProcMemo() {
        return procMemo;
    }

    public void setProcMemo(String procMemo) {
        this.procMemo = procMemo;
    }

    public String getProcUser() {
        return procUser;
    }

    public void setProcUser(String procUser) {
        this.procUser = procUser;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

}
