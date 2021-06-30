package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 勞保年金平均薪資月數參數維護修改作業 
 * 查詢條件輸入頁面
 * bapa0x110q.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class AvgAmtMonMaintQueryForm extends BaseValidatorForm {
    private String method;
    private String effYear; // 施行年度

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
    
}
