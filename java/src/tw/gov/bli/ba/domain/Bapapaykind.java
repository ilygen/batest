package tw.gov.bli.ba.domain;

import java.io.Serializable;

public class Bapapaykind implements Serializable{
    private String reType;//收支區分
    private String payKind;//給付種類
    private String payItem;//給付項目
    private String imk;//保險別
    private String account;//會計明細科目
    private String department;//承辦科別
    public String getReType() {
        return reType;
    }
    public void setReType(String reType) {
        this.reType = reType;
    }
    public String getPayKind() {
        return payKind;
    }
    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }
    public String getPayItem() {
        return payItem;
    }
    public void setPayItem(String payItem) {
        this.payItem = payItem;
    }
    public String getImk() {
        return imk;
    }
    public void setImk(String imk) {
        this.imk = imk;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

}
