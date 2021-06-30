package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 決行層級參數檔 (<code>BAPADCSLVL</code>)
 * 
 * @author swim
 */
@Table("BAPADCSLVL")
public class Bapadcslvl implements Serializable {
    @PkeyField("PAYCODE")
    private String payCode; // 給付別
    private String payKind; // 給付種類
    private String rechklvl; // 決行層級代碼
    private BigDecimal stdAmt; // 決行金額
    private String hicLevel; // 失能等級限制
    private String nlwkmk; // 普職限制
    private String echkLevel; // 錯誤程度決行權限
    private String ochkLevel; // 穿透程度決行權限
    private String wchkLevel; // 警告程度決行權限
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間
    
    public String getPayCode() {
        return payCode;
    }
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public String getRechklvl() {
        return rechklvl;
    }
    public void setRechklvl(String rechklvl) {
        this.rechklvl = rechklvl;
    }
    public BigDecimal getStdAmt() {
        return stdAmt;
    }
    public void setStdAmt(BigDecimal stdAmt) {
        this.stdAmt = stdAmt;
    }
    public String getHicLevel() {
        return hicLevel;
    }
    public void setHicLevel(String hicLevel) {
        this.hicLevel = hicLevel;
    }
    public String getNlwkmk() {
        return nlwkmk;
    }
    public void setNlwkmk(String nlwkmk) {
        this.nlwkmk = nlwkmk;
    }
    public String getEchkLevel() {
        return echkLevel;
    }
    public void setEchkLevel(String echkLevel) {
        this.echkLevel = echkLevel;
    }
    public String getOchkLevel() {
        return ochkLevel;
    }
    public void setOchkLevel(String ochkLevel) {
        this.ochkLevel = ochkLevel;
    }
    public String getWchkLevel() {
        return wchkLevel;
    }
    public void setWchkLevel(String wchkLevel) {
        this.wchkLevel = wchkLevel;
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
    public String getPayKind() {
        return payKind;
    }
    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }
    
}
