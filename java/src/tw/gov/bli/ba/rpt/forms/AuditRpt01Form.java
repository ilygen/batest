package tw.gov.bli.ba.rpt.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 稽催管制相關報表 - 逾期未決行案件管制表 - 查詢頁面 (balp0d420l.jsp)
 * 
 * @author Noctis
 */
public class AuditRpt01Form extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String endYm; // 截止年月
    private String prtTyp; // 報表種類

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

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

	public String getEndYm() {
		return endYm;
	}

	public void setEndYm(String endYm) {
		this.endYm = endYm;
	}

	public String getPrtTyp() {
		return prtTyp;
	}

	public void setPrtTyp(String prtTyp) {
		this.prtTyp = prtTyp;
	}

}
