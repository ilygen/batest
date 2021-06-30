package tw.gov.bli.ba.domain;

import java.io.Serializable;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 編審註記參數檔 (<code>BAPADCHK</code>)
 * 
 * @author swim
 */
@Table("BAPADCHK")
public class Bapadchk implements Serializable {
    @PkeyField("CHKTYP")
    private String chkTyp; // 編審註記種類
    
    @PkeyField("CHKCODE")
    private String chkCode; // 編審註記代號
    
    @PkeyField("CHKGROUP")    
    private String chkGroup; // 編審註記類別
    
    private String chkObj; // 給付對象   
    private String valibDate; // 有效日期起
    private String valieDate; // 有效日期迄
    private String chkDesc; // 編審註記說明
    private String chkLevel; // 編審註記程度
    private String chkCondesc; // 編審條件
    private String chkLawdesc; // 法令依據
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間
    
    public Bapadchk() {
    }
    
    public String getChkTyp() {
        return chkTyp;
    }
    public void setChkTyp(String chkTyp) {
        this.chkTyp = chkTyp;
    }
    public String getChkCode() {
        return chkCode;
    }
    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }
    public String getChkGroup() {
        return chkGroup;
    }
    public void setChkGroup(String chkGroup) {
        this.chkGroup = chkGroup;
    }
    public String getChkObj() {
        return chkObj;
    }
    public void setChkObj(String chkObj) {
        this.chkObj = chkObj;
    }
    public String getValibDate() {
        return valibDate;
    }
    public void setValibDate(String valibDate) {
        this.valibDate = valibDate;
    }
    public String getValieDate() {
        return valieDate;
    }
    public void setValieDate(String valieDate) {
        this.valieDate = valieDate;
    }
    public String getChkDesc() {
        return chkDesc;
    }
    public void setChkDesc(String chkDesc) {
        this.chkDesc = chkDesc;
    }
    public String getChkLevel() {
        return chkLevel;
    }
    public void setChkLevel(String chkLevel) {
        this.chkLevel = chkLevel;
    }
    public String getChkCondesc() {
        return chkCondesc;
    }
    public void setChkCondesc(String chkCondesc) {
        this.chkCondesc = chkCondesc;
    }
    public String getChkLawdesc() {
        return chkLawdesc;
    }
    public void setChkLawdesc(String chkLawdesc) {
        this.chkLawdesc = chkLawdesc;
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
