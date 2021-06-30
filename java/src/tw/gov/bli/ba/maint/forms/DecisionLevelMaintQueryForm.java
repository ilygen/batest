package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 決行層級維護
 * 查詢條件輸入頁面
 * bapa0x030f.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class DecisionLevelMaintQueryForm extends BaseValidatorForm{
    private String method;
    private String payCode; // 給付種類

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
}
