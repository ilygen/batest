package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 月核定處理相關報表 - 月處理異動清單 - 查詢頁面 (balp0d3r0l.jsp)
 * 
 * @author Noctis
 */
public class MonthlyBatchRptForm extends BaseValidatorForm {
    private String method;
    
    private String payCode; // 給付別
    private String mtestMk; // 月核定別
    private String issuYm; // 核定年月
    private String chkDate; // 核定日期
    
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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getMtestMk() {
        return mtestMk;
    }

    public void setMtestMk(String mtestMk) {
        this.mtestMk = mtestMk;
    }

	public String getChkDate() {
		return chkDate;
	}

	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}
    
}
