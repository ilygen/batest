package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 核定通知書維護
 * 查詢條件輸入頁面
 * bapa0x020f.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class AdviceMaintQueryForm extends BaseValidatorForm {
    private String method;
    private String payCode; // 給付別
    private String authTyp; // 核定格式

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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getAuthTyp() {
        return authTyp;
    }

    public void setAuthTyp(String authTyp) {
        this.authTyp = authTyp;
    }
}
