package tw.gov.bli.ba.domain;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 年資維護介接檔 (<code>BASENIMAINT</code>)
 * 
 * @author Rickychi
 */
@Table("BASENIMAINT")
public class Basenimaint {
    @PkeyField("APNO")
    private String apNo;// 受理編號

    @PkeyField("SEQNO")
    private String seqNo;// 序號

    @PkeyField("SENITYP")
    private String seniTyp;// 年資類別

    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String evtName;// 事故者姓名
    private String ubno;// 保險證號
    private String ubnoCk;// 保險證號檢查碼
    private String owesBdate;// 扣除期間起日
    private String owesEdate;// 扣除期間迄日
    private String owesMk;// 欠費註記
    private String oldSeniab;// 一次請領之年資採計方式
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間

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

    public String getSeniTyp() {
        return seniTyp;
    }

    public void setSeniTyp(String seniTyp) {
        this.seniTyp = seniTyp;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getUbno() {
        return ubno;
    }

    public void setUbno(String ubno) {
        this.ubno = ubno;
    }

    public String getUbnoCk() {
        return ubnoCk;
    }

    public void setUbnoCk(String ubnoCk) {
        this.ubnoCk = ubnoCk;
    }

    public String getOwesBdate() {
        return owesBdate;
    }

    public void setOwesBdate(String owesBdate) {
        this.owesBdate = owesBdate;
    }

    public String getOwesEdate() {
        return owesEdate;
    }

    public void setOwesEdate(String owesEdate) {
        this.owesEdate = owesEdate;
    }

    public String getOwesMk() {
        return owesMk;
    }

    public void setOwesMk(String owesMk) {
        this.owesMk = owesMk;
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

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }
}
