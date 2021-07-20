package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 月核定處理相關報表 - 核付清單及核付明細表 - 查詢頁面 (balp0d3a0l.jsp)
 * 
 * @author Goston
 */
public class MonthlyRpt10Form extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String issuYm; // 核定年月
    private String chkDate; //核定日期
    private String reportType; // 報表種類
    private String npWithLip; // （是否）勞併國    
    private String downloadFileName;//下載名稱
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        reportType = "1";
        npWithLip = "";        
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
    
    
    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getNpWithLip() {
        return npWithLip;
    }

    public void setNpWithLip(String npWithLip) {
        this.npWithLip = npWithLip;
    }

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}       

}
