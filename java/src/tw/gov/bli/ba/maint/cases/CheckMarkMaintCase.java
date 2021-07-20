package tw.gov.bli.ba.maint.cases;

import java.io.Serializable;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 編審註記維護作業
 * 
 * @author swim
 */
public class CheckMarkMaintCase implements Serializable {
    private String chkTyp; // 編審註記種類
    private String chkCode; // 編審註記代號
    private String chkGroup; // 編審註記類別
    private String chkObj; // 給付對象代碼 
    private String chkObjName; // 給付對象
    private String valibDate; // 有效日期起
    private String valieDate; // 有效日期迄
    private String chkDesc; // 編審註記說明
    private String chkLevel; // 編審註記程度代碼
    private String chkLevelName; // 編審註記程度
    private String chkCondesc; // 編審條件
    private String chkLawdesc; // 法令依據
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間
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
    public String getChkObjName() {
        return chkObjName;
    }
    public void setChkObjName(String chkObjName) {
        this.chkObjName = chkObjName;
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
    public String getChkLevelName() {
        return chkLevelName;
    }
    public void setChkLevelName(String chkLevelName) {
        this.chkLevelName = chkLevelName;
    }
    public String getValibDateStr() {
        return DateUtil.changeDateType(valibDate);
    }
    public String getValieDateStr() {
        return DateUtil.changeDateType(valieDate);
    }
    public String getChkObjStr() {
        if(chkObj.equals("L"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        else if(chkObj.equals("K"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        else if(chkObj.equals("S"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        else
            return "";
    }
    public String getChkLevelStr() {
        if(chkLevel.equals("O"))
            return ConstantKey.BACHKFILE_CHKCODE_O;
        else if(chkLevel.equals("W"))
            return ConstantKey.BACHKFILE_CHKCODE_W;
        else if(chkLevel.equals("E"))
            return ConstantKey.BACHKFILE_CHKCODE_E;
        else if(chkLevel.equals("G"))
            return ConstantKey.BACHKFILE_CHKCODE_G;
        else
            return "";
    }
    
}
