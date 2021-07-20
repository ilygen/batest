package tw.gov.bli.ba.domain;

import java.io.Serializable;

/**
 * 被保險人承保身分證號重號檔
 * 
 * @author Rickychi
 */
public class Badupeidn implements Serializable{
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String idnNo;// 身分證號
    private String name;// 姓名
    private String brDate;// 出生日期
    private String selMk;// 選擇註記
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

    public String getIdnNo() {
        return idnNo;
    }

    public void setIdnNo(String idnNo) {
        this.idnNo = idnNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrDate() {
        return brDate;
    }

    public void setBrDate(String brDate) {
        this.brDate = brDate;
    }

    public String getSelMk() {
        return selMk;
    }

    public void setSelMk(String selMk) {
        this.selMk = selMk;
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
