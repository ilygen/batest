package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * case for 媒體檔產製下載
 * 
 * @author Zehua
 */
public class MediaOnlineDownloadCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private int rowNum;
    private String mfileName; // 媒體檔案名稱
    private String mfileDate; // 媒體檔案日期
    private String payCodeStr; // 給付別 K:失能 L:老年 S:遺屬
    private String issuYm; // 核定年月
    private String chkDate; // 核定日期
    private BigDecimal ftpSeq; //上傳次數

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getMfileName() {
        return mfileName;
    }

    public void setMfileName(String mfileName) {
        this.mfileName = mfileName;
    }

    public String getMfileDate() {
        return mfileDate;
    }

    public void setMfileDate(String mfileDate) {
        this.mfileDate = mfileDate;
    }

    public String getPayCodeStr() {
        return payCodeStr;
    }

    public void setPayCodeStr(String payCodeStr) {
        this.payCodeStr = payCodeStr;
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

    public BigDecimal getFtpSeq() {
        return ftpSeq;
    }

    public void setFtpSeq(BigDecimal ftpSeq) {
        this.ftpSeq = ftpSeq;
    }
}
