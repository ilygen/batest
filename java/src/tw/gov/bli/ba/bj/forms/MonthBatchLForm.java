package tw.gov.bli.ba.bj.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 批次月處理作業 - 老年年金批次月處理作業 - 查詢頁面  (baba0m050x.jsp)
 * 
 * @author Noctis
 *
 */
public class MonthBatchLForm extends BaseValidatorForm {
    
    private String method;

    private String procType; // 月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定)
    private String issuYm; // 核定年月
    private String caseTyp; // 案件類別
    
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
    
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

	public String getProcType() {
		return procType;
	}

	public void setProcType(String procType) {
		this.procType = procType;
	}

	public String getCaseTyp() {
		return caseTyp;
	}

	public void setCaseTyp(String caseTyp) {
		this.caseTyp = caseTyp;
	}
   
    
}
