package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 1. 	老年年金所得替代率參數維護作業 
 * 查詢條件輸入頁面
 * bapa0x120q.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class DisabledReplacementRatioMaintQueryForm extends BaseValidatorForm {
    private String method;
    private String effYm; // 施行年度

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

	public String getEffYm() {
		return effYm;
	}

	public void setEffYm(String effYm) {
		this.effYm = effYm;
	}

}
