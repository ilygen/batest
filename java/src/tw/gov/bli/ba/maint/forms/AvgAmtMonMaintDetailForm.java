package tw.gov.bli.ba.maint.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class AvgAmtMonMaintDetailForm extends BaseValidatorForm {
    private String method;
    
    private String effYear;     // 施行年度
    private String appMonth;    // 採計月數
    private String effYearOrg;     // 查詢的施行年度
    private String user;     // 修改人員
    private String date;     // 修改日期

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

	public String getEffYear() {
		return effYear;
	}

	public void setEffYear(String effYear) {
		this.effYear = effYear;
	}

	public String getAppMonth() {
		return appMonth;
	}

	public void setAppMonth(String appMonth) {
		this.appMonth = appMonth;
	}

	public String getEffYearOrg() {
		return effYearOrg;
	}

	public void setEffYearOrg(String effYearOrg) {
		this.effYearOrg = effYearOrg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
}
