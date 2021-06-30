package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;

/**
 * 不合格核定通知紀錄檔
 * 
 * @author KIYOMI
 */
public class Baunqualifiednotice implements Serializable {
    
    @PkeyField("BAAPPBASEID")
    private BigDecimal baappbaseId;// 資料列編號

    @LogField("APNO")
    private String apNo;// 受理編號

    @LogField("SEQNO")
    private String seqNo;// 序號

    @LogField("CASETYP")
    private String caseTyp;// 案件類別

    @LogField("BENIDNNO")
    private String benIdnNo;// 受益人身分證號

    @LogField("BENNAME")
    private String benName;// 受益人姓名

    @LogField("ISSUYM")
    private String issuYm;// 核定年月

    @LogField("UNQUALIFIEDCAUSE")
    private String unqualifiedCause;// 不合格原因

    @LogField("NOTIFYFORM")
    private String notifyForm;// 核定通知書格式
    
    @LogField("UNQUALIFIEDCAUSEDESC")
    private String unqualifiedCauseDesc;// 不合格原因中文描述
    
    @LogField("CRTUSER")
    private String crtUser;// 新增者代號

    @LogField("CRTTIME")
    private String crtTime;// 新增日期時間


    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
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
    
    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }    
    
    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }
    
    public String getUnqualifiedCause() {
        return unqualifiedCause;
    }

    public void setUnqualifiedCause(String unqualifiedCause) {
        this.unqualifiedCause = unqualifiedCause;
    }
    
    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }
    
    public String getUnqualifiedCauseDesc() {
        return unqualifiedCauseDesc;
    }

    public void setUnqualifiedCauseDesc(String unqualifiedCauseDesc) {
        this.unqualifiedCauseDesc = unqualifiedCauseDesc;
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
