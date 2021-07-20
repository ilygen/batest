package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 月核定處理相關報表 - 補送在學證明通知函 - 查詢頁面 (balp0d3p0l.jsp)
 * 
 * @author Noctis
 */

public class MonthlyRpt29Form extends BaseValidatorForm {
    
    private String method;
    
    private String payCode; // 給付別
    private String rptKind; // 1=每月,2=每年
    private String studeChkMonth; // 在學結束月份 sql判斷條件
    private String studeDate; // 在學結束月份
    
    private String downloadFileName;
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
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

	public String getRptKind() {
		return rptKind;
	}

	public void setRptKind(String rptKind) {
		this.rptKind = rptKind;
	}

	public String getStudeChkMonth() {
		return studeChkMonth;
	}

	public void setStudeChkMonth(String studeChkMonth) {
		this.studeChkMonth = studeChkMonth;
	}

	public String getStudeDate() {
		return studeDate;
	}

	public void setStudeDate(String studeDate) {
		this.studeDate = studeDate;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
    
}
