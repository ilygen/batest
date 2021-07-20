package tw.gov.bli.ba.query.forms;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 查詢作業 - 年金統計查詢 - 查詢頁面 (baiq0d070q.jsp)
 * 
 * @author Eddie
 */
public class AnnuityStatisticsForm extends BaseValidatorForm {
	private String payCode; // 給付別
	private String caseClassification;// 案件類別
	private String paymentYMStart; // 核付年月起
	private String paymentYMEnd; // 核付年月迄
	private String analysisSelect; // 分析選項
	private String qrytype; // 統計項目
	private String[] spacing; // 級距區間

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (page != 1 && page != 2)
            return null;

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }
    
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getCaseClassification() {
		return caseClassification;
	}

	public void setCaseClassification(String caseClassification) {
		this.caseClassification = caseClassification;
	}

	public String getPaymentYMStart() {
		return paymentYMStart;
	}

	public void setPaymentYMStart(String paymentYMStart) {
		this.paymentYMStart = paymentYMStart;
	}

	public String getPaymentYMEnd() {
		return paymentYMEnd;
	}

	public void setPaymentYMEnd(String paymentYMEnd) {
		this.paymentYMEnd = paymentYMEnd;
	}

	public String getAnalysisSelect() {
		return analysisSelect;
	}

	public void setAnalysisSelect(String analysisSelect) {
		this.analysisSelect = analysisSelect;
	}

	public String getQrytype() {
		return qrytype;
	}

	public void setQrytype(String qrytype) {
		this.qrytype = qrytype;
	}

	public String[] getSpacing() {
		return spacing;
	}

	public void setSpacing(String[] spacing) {
		this.spacing = spacing;
	}

}
