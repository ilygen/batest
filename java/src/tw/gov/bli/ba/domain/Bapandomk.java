package tw.gov.bli.ba.domain;

import java.io.Serializable;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 處理註記參數檔
 * 
 * @author jerry
 */

@Table("BAPANDOMK")
public class Bapandomk implements Serializable{
    @PkeyField("letterType")
    private String letterType;  //函別
    @PkeyField("ndomk")
    private String ndomk;       //處理註記代碼
    private String lpaymk;      //老年給付註記
    private String kpaymk;      //失能給付註記
    private String spaymk;      //遺屬給付註記
    private String ndomkName;   //處理註記名稱
    private String crtUser;     //新增者代號
    private String crtTime;     //新增日期時間
    private String updUser;     //異動者代號
    private String updTime;     //異動日期時間
    public String getLetterType() {
        return letterType;
    }
    public void setLetterType(String letterType) {
        this.letterType = letterType;
    }
    public String getNdomk() {
        return ndomk;
    }
    public void setNdomk(String ndomk) {
        this.ndomk = ndomk;
    }
    public String getLpaymk() {
        return lpaymk;
    }
    public void setLpaymk(String lpaymk) {
        this.lpaymk = lpaymk;
    }
    public String getKpaymk() {
        return kpaymk;
    }
    public void setKpaymk(String kpaymk) {
        this.kpaymk = kpaymk;
    }
    public String getSpaymk() {
        return spaymk;
    }
    public void setSpaymk(String spaymk) {
        this.spaymk = spaymk;
    }
    public String getNdomkName() {
        return ndomkName;
    }
    public void setNdomkName(String ndomkName) {
        this.ndomkName = ndomkName;
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
