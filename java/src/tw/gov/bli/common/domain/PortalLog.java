package tw.gov.bli.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Portal Log Domain 物件
 * 
 * @author Goston
 */
public class PortalLog implements Serializable {
    /**
     * 編號
     */
    private BigDecimal sysId;

    /**
     * 紀錄時間
     */
    private String logDateTime;

    /**
     * 用戶代號
     */
    private String userId;

    /**
     * 用戶 IP 位址
     */
    private String userIP;

    /**
     * 用戶執行動作
     */
    private String userAction;

    /**
     * 應用系統代號
     */
    private String apCode;

    /**
     * 應用系統名稱
     */
    private String apName;

    /**
     * 應用系統功能代號
     */
    private String apFunctionCode;

    /**
     * 應用系統功能名稱
     */
    private String apFunctionName;

    /**
     * 應用系統網址
     */
    private String apUrl;

    /**
     * 說明
     */
    private String logDescript;

    /**
     * 系統時間
     */
    private Timestamp dateTime;

    /**
     * 檢查資訊
     */
    private String token;

    public PortalLog() {

    }

    public BigDecimal getSysId() {
        return sysId;
    }

    public void setSysId(BigDecimal sysId) {
        this.sysId = sysId;
    }

    public String getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(String logDateTime) {
        this.logDateTime = logDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getApCode() {
        return apCode;
    }

    public void setApCode(String apCode) {
        this.apCode = apCode;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getApFunctionCode() {
        return apFunctionCode;
    }

    public void setApFunctionCode(String apFunctionCode) {
        this.apFunctionCode = apFunctionCode;
    }

    public String getApFunctionName() {
        return apFunctionName;
    }

    public void setApFunctionName(String apFunctionName) {
        this.apFunctionName = apFunctionName;
    }

    public String getApUrl() {
        return apUrl;
    }

    public void setApUrl(String apUrl) {
        this.apUrl = apUrl;
    }

    public String getLogDescript() {
        return logDescript;
    }

    public void setLogDescript(String logDescript) {
        this.logDescript = logDescript;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
