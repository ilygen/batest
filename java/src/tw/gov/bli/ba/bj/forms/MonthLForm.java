package tw.gov.bli.ba.bj.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;




/**
 * 批次處理 - 線上月核定作業 - 遺屬年金線上月核定作業 - 查詢頁面  (baba0m110x.jsp)
 * 
 * @author Noctis
 *
 */
public class MonthLForm extends BaseValidatorForm {
    
    private String method;

    private String issuYm; // 核定年月
    private String chkDate; //核定日期
    
    private String apNoOfConfirm; // 受理編號
    private String checkCount; // 查詢系統日期是否介於批次月試核及月核定期間，若是則[確認]鍵disable。
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        issuYm="";
        chkDate="";          
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
    
    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

	public String getApNoOfConfirm() {
		return apNoOfConfirm;
	}

	public void setApNoOfConfirm(String apNoOfConfirm) {
		this.apNoOfConfirm = apNoOfConfirm;
	}

	public String getCheckCount() {
		return checkCount;
	}

	public void setCheckCount(String checkCount) {
		this.checkCount = checkCount;
	}

}
