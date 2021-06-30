package tw.gov.bli.ba.domain;

import tw.gov.bli.common.annotation.Table;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 給付案件附件檔
 * 
 * @author Eddie
 */
@Table("BAAPPATTFILE")
public class Baappattfile implements Serializable {
    private BigDecimal baappattfileId;// 資料列編號
    private String apNo;// 受理編號
    private String appDate;// 申請日期
    private String checkIn;// 來源別
    private byte[] attFile;// 附件檔案
    private String procUser;// 處理人員
    private String procTime;// 處理時間

    public BigDecimal getBaappattfileId() {
        return baappattfileId;
    }

    public void setBaappattfileId(BigDecimal baappattfileId) {
        this.baappattfileId = baappattfileId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public byte[] getAttFile() {
        return attFile;
    }

    public void setAttFile(byte[] attFile) {
        this.attFile = attFile;
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
}
