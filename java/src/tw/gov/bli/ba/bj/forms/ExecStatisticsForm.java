package tw.gov.bli.ba.bj.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 年金統計執行作業 (BAST0M270S)
 * 
 * @author TseHua
 */
public class ExecStatisticsForm  extends BaseValidatorForm {
	
	private static final long serialVersionUID = -2514270678750628903L;
	private String issuYm;//處裡日期
	private String baJobId;//批次id
	private String method;
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        issuYm="";      
    }

	public String getIssuYm() {
		return issuYm;
	}

	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBaJobId() {
		return baJobId;
	}

	public void setBaJobId(String baJobId) {
		this.baJobId = baJobId;
	}
}
