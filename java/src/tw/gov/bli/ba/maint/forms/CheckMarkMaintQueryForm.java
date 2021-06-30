package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 編審註記維護
 * 查詢條件輸入頁面
 * bapa0x010f.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class CheckMarkMaintQueryForm extends BaseValidatorForm {
    private String method;
    private String chkObj; // 給付種類
    private String chkGroup; // 類別
    private String chkCode; // 編審註記代號

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
}
