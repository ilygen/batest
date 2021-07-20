package tw.gov.bli.ba.maint.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class BasicAmtMaintQueryForm extends BaseValidatorForm {
    private String method;
    private String payYmB; // 給付年月起月
    private String payCode;

    

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }

    public String getPayYmB() {
		return payYmB;
	}

	public void setPayYmB(String payYmB) {
		this.payYmB = payYmB;
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
