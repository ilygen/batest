package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 編審註記查詢
 * 查詢條件輸入頁面
 * bapa0x011a.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class CheckMarkMaintDetailForm extends BaseValidatorForm {
    private String method;
    private String chkObj; // 給付種類
    private String chkGroup; // 類別
    private String chkCode; // 編審註記代號
    private String chkLevel; // 嚴重程度
    private String valibDate; // 有效日期起
    private String valieDate; // 有效日期迄
    private String chkDesc; // 中文說明
    private String chkCondesc; // 編審條件
    private String chkLawdesc; // 法令依據

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getChkObj() {
        return chkObj;
    }

    public void setChkObj(String chkObj) {
        this.chkObj = chkObj;
    }

    public String getChkGroup() {
        return chkGroup;
    }

    public void setChkGroup(String chkGroup) {
        this.chkGroup = chkGroup;
    }

    public String getChkCode() {
        return chkCode;
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }

    public String getChkLevel() {
        return chkLevel;
    }

    public void setChkLevel(String chkLevel) {
        this.chkLevel = chkLevel;
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

    
}