package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 月核定日期參數檔 (<code>BAPAISSUDATE</code>)
 * 
 * @author Goston
 */
public class Bapaissudate implements Serializable {
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String issuDate; // 核定日期
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間

    public Bapaissudate() {

    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getIssuDate() {
        return issuDate;
    }

    public void setIssuDate(String issuDate) {
        this.issuDate = issuDate;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

}
