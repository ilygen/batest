package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 系統參數檔 (<code>BAPARAM</code>)
 * 
 * @author Goston
 */
@Table("BAPARAM")
public class Baparam implements Serializable {
    @PkeyField("PARAMTYP")
    private String paramTyp; // 參數類別 0: 內部參數 1: 共用參數

    @PkeyField("PARAMGROUP")
    private String paramGroup; // 參數種類

    @PkeyField("PARAMCODE")
    private String paramCode; // 參數代號

    private String paramSeq; // 參數順序
    private String paramName; // 參數名稱
    private String paramDesc; // 參數說明
    private String paramUseMk; // 使用狀態註記
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間

    public Baparam() {
    }

    public String getParamTyp() {
        return paramTyp;
    }

    public void setParamTyp(String paramTyp) {
        this.paramTyp = paramTyp;
    }

    public String getParamGroup() {
        return paramGroup;
    }

    public void setParamGroup(String paramGroup) {
        this.paramGroup = paramGroup;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamSeq() {
        return paramSeq;
    }

    public void setParamSeq(String paramSeq) {
        this.paramSeq = paramSeq;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public String getParamUseMk() {
        return paramUseMk;
    }

    public void setParamUseMk(String paramUseMk) {
        this.paramUseMk = paramUseMk;
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
