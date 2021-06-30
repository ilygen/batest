package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 物價指數調整紀錄維護
 * 查詢條件輸入頁面
 * bapa0x070f.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class CpiRecMaintQueryForm extends BaseValidatorForm {
    private String method;
    private String issuYear; // 指數年度

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

	public String getIssuYear() {
		return issuYear;
	}

	public void setIssuYear(String issuYear) {
		this.issuYear = issuYear;
	}

}
