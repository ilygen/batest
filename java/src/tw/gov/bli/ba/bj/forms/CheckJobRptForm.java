package tw.gov.bli.ba.bj.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 檢核作業 - 產生檢核案件作業 - 查詢頁面  (baba0m200x.jsp)
 * 
 * @author Noctis
 */
public class CheckJobRptForm extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String issuYm; // 核定年月

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (page != 1)
            return null;
        
        ActionErrors errors = super.validate(mapping, request);
        
        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        payCode = "";
        issuYm = "";
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

	public String getIssuYm() {
		return issuYm;
	}

	public void setIssuYm(String issuYm) {
		this.issuYm = issuYm;
	}

}
