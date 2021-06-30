package tw.gov.bli.ba.bj.forms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 批次排程作業 - 批次程式查詢作業 - 查詢頁面  (baba0m240x.jsp)
 * 
 * @author KIYOMI
 */
public class QryProcedureForm extends BaseValidatorForm {
    private String method;

    private String payCode;            // 給付別
    private String issuYm;             // 核定年月
    private String confirmMkOfConfirm; //
    
    private String updateDateBegin;    // 處理時間 (起)
    private String updateDateEnd;      // 處理時間 (迄)    

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
        updateDateBegin = "";
        updateDateEnd = "";
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

	public String getConfirmMkOfConfirm() {
		return confirmMkOfConfirm;
	}

	public void setConfirmMkOfConfirm(String confirmMkOfConfirm) {
		this.confirmMkOfConfirm = confirmMkOfConfirm;
	}
	
    public String getUpdateDateBegin() {
        return updateDateBegin;
    }

    public void setUpdateDateBegin(String updateDateBegin) {
        this.updateDateBegin = updateDateBegin;
    }

    public String getUpdateDateEnd() {
        return updateDateEnd;
    }

    public void setUpdateDateEnd(String updateDateEnd) {
        this.updateDateEnd = updateDateEnd;
    }	
	
}
