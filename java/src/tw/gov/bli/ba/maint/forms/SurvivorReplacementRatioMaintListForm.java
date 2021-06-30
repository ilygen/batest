package tw.gov.bli.ba.maint.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class SurvivorReplacementRatioMaintListForm extends BaseValidatorForm {
    private String method;
    private String effYmOrg; //原查詢條件

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

	public String getEffYmOrg() {
		return effYmOrg;
	}

	public void setEffYmOrg(String effYmOrg) {
		this.effYmOrg = effYmOrg;
	}

}
