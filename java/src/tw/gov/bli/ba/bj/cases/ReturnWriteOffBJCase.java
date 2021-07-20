package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * case for 收回作業
 * 
 * @author swim
 */
public class ReturnWriteOffBJCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private BigDecimal baBatchRecId; // 資料列編號
    private BigDecimal dataCount; // 筆數
    private String stTime; // 開始時間
    private String endTime; // 結束時間
    private String executeTime; // 執行/耗費時間
    private String fileName; // 檔案名稱
    private String upTyp; // 資料來源類別
    private String upTime; // 檔案產生時間
    private String upOrgan; // 資料來源
    private String upOrganId; // 資料來源
    private BigDecimal amount; // 總金額
    private String procStat; // 處理狀態
    private String procMemo; // 訊息說 明
    private String procUser; // 處理人員
    private String procTime; // 處理日期

    public String getUpOrgan() {
        return upOrgan;
    }

    public void setUpOrgan(String upOrgan) {
        this.upOrgan = upOrgan;
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

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
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

    public BigDecimal getBaBatchRecId() {
        return baBatchRecId;
    }

    public void setBaBatchRecId(BigDecimal baBatchRecId) {
        this.baBatchRecId = baBatchRecId;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public String getUpTyp() {
        return upTyp;
    }

    public void setUpTyp(String upTyp) {
        this.upTyp = upTyp;
    }

    public String getUpOrganId() {
        return upOrganId;
    }

    public void setUpOrganId(String upOrganId) {
        this.upOrganId = upOrganId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStTimeStr() {
        if (StringUtils.isNotEmpty(stTime))
            return DateUtility.changeDateType(stTime);
        else
            return "";
    }

    public String getEndTimeStr() {
        if (StringUtils.isNotEmpty(endTime))
            return DateUtility.changeDateType(endTime);
        else
            return "";
    }

    public String getExecuteTimeStr() {
        if (StringUtils.isNotEmpty(executeTime))
            return DateUtility.changeDateType(executeTime);
        else
            return "";
    }

    public String getUpTimeStr() {
        if (StringUtils.isNotEmpty(upTime))
            return DateUtility.changeDateType(upTime);
        else
            return "";
    }

    public String getProcTimeStr() {
        if (StringUtils.isNotEmpty(procTime))
            return DateUtility.changeDateType(procTime);
        else
            return "";
    }

    public String getProcStatStr() {
        if (StringUtils.isNotEmpty(procStat)) {
            if (procStat.equals(ConstantKey.BABATCHREC_PROCSTAT_0))
                return ConstantKey.BABATCHREC_PROCSTAT_STR_0;
            else if (procStat.equals(ConstantKey.BABATCHREC_PROCSTAT_1))
                return ConstantKey.BABATCHREC_PROCSTAT_STR_1;
            else if (procStat.equals(ConstantKey.BABATCHREC_PROCSTAT_2))
                return ConstantKey.BABATCHREC_PROCSTAT_STR_2;
            else if (procStat.equals(ConstantKey.BABATCHREC_PROCSTAT_3))
                return ConstantKey.BABATCHREC_PROCSTAT_STR_3;
            else if (procStat.equals(ConstantKey.BABATCHREC_PROCSTAT_4))
                return ConstantKey.BABATCHREC_PROCSTAT_STR_4;
            else if (procStat.equals(ConstantKey.BABATCHREC_PROCSTAT_5))
                return ConstantKey.BABATCHREC_PROCSTAT_STR_5;
            else
                return "";
        }
        else
            return "";
    }
}
