package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;

/**
 * 老年差額金通知發文紀錄檔
 * 
 * @author KIYOMI
 */
public class Bamarginamtnotify implements Serializable {

    @LogField("PROGID")
    private String progId; // 程式代號

    @LogField("APNO")
    private String apNo; // 受理編號

    @LogField("SEQNO")
    private String seqNo; // 序號

    @LogField("ISSUYM1")
    private String issuYm1; // 核定年月 1

    @LogField("ISSUYM2")
    private String issuYm2; // 核定年月 2

    @LogField("ISSUYM3")
    private String issuYm3; // 核定年月 3

    @PkeyField("PROCDATE1")
    private String procDate1; // 處理日期 1

    @PkeyField("PROCDATE2")
    private String procDate2; // 處理日期 2

    @PkeyField("PROCDATE3")
    private String procDate3; // 處理日期 3

    @PkeyField("ISSUEDATE1")
    private String issueDate1; // 發文日期 1

    @PkeyField("ISSUEDATE2")
    private String issueDate2; // 發文日期 2

    @PkeyField("ISSUEDATE3")
    private String issueDate3; // 發文日期 3

    @LogField("NOTIFYFORM")
    private String notifyForm; // 發文格式

    @PkeyField("MAADMRECID1")
    private BigDecimal maAdmRecId1; // 行政支援紀錄編號 1

    @PkeyField("MAADMRECID2")
    private BigDecimal maAdmRecId2; // 行政支援紀錄編號 2

    @PkeyField("MAADMRECID3")
    private BigDecimal maAdmRecId3; // 行政支援紀錄編號 3

    @LogField("CRTUSER")
    private String crtUser; // 新增者代號

    @LogField("CRTTIME")
    private String crtTime; // 新增日期時間

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIssuYm1() {
        return issuYm1;
    }

    public void setIssuYm1(String issuYm1) {
        this.issuYm1 = issuYm1;
    }

    public String getIssuYm2() {
        return issuYm2;
    }

    public void setIssuYm2(String issuYm2) {
        this.issuYm2 = issuYm2;
    }

    public String getIssuYm3() {
        return issuYm3;
    }

    public void setIssuYm3(String issuYm3) {
        this.issuYm3 = issuYm3;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public String getProcDate1() {
        return procDate1;
    }

    public void setProcDate1(String procDate1) {
        this.procDate1 = procDate1;
    }

    public String getProcDate2() {
        return procDate2;
    }

    public void setProcDate2(String procDate2) {
        this.procDate2 = procDate2;
    }

    public String getProcDate3() {
        return procDate3;
    }

    public void setProcDate3(String procDate3) {
        this.procDate3 = procDate3;
    }

    public String getIssueDate1() {
        return issueDate1;
    }

    public void setIssueDate1(String issueDate1) {
        this.issueDate1 = issueDate1;
    }

    public String getIssueDate2() {
        return issueDate2;
    }

    public void setIssueDate2(String issueDate2) {
        this.issueDate2 = issueDate2;
    }

    public String getIssueDate3() {
        return issueDate3;
    }

    public void setIssueDate3(String issueDate3) {
        this.issueDate3 = issueDate3;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public BigDecimal getMaAdmRecId1() {
        return maAdmRecId1;
    }

    public void setMaAdmRecId1(BigDecimal maAdmRecId1) {
        this.maAdmRecId1 = maAdmRecId1;
    }

    public BigDecimal getMaAdmRecId2() {
        return maAdmRecId2;
    }

    public void setMaAdmRecId2(BigDecimal maAdmRecId2) {
        this.maAdmRecId2 = maAdmRecId2;
    }

    public BigDecimal getMaAdmRecId3() {
        return maAdmRecId3;
    }

    public void setMaAdmRecId3(BigDecimal maAdmRecId3) {
        this.maAdmRecId3 = maAdmRecId3;
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

}
