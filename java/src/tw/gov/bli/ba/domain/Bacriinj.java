package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 國際疾病代碼參數檔 (<code>BACRIINJ</code>)
 * 
 * @author Goston
 */
@Table("BACRIINJ")
public class Bacriinj implements Serializable {
    @PkeyField("BACRIINJ")
    private String criInJMk; // 國際疾病代碼
    private String criInJName; // 國際疾病名稱
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間

    public Bacriinj() {

    }

    public String getCriInJMk() {
        return criInJMk;
    }

    public void setCriInJMk(String criInJMk) {
        this.criInJMk = criInJMk;
    }

    public String getCriInJName() {
        return criInJName;
    }

    public void setCriInJName(String criInJName) {
        this.criInJName = criInJName;
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
