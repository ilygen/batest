package tw.gov.bli.ba.rpt.forms;


import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 其他類報表 - 轉催收核定清單 - 查詢頁面 (balp0d650l.jsp)
 * 
 * @author ZeHua
 */
public class OtherRpt06Form extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String procYm; // 處理年月
    private String npWithLip; //（是否）勞併國    

    private String demDate;//轉催收日期
    
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
        payCode = "L";
        procYm="";
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

	public String getProcYm() {
		return procYm;
	}

	public void setProcYm(String procYm) {
		this.procYm = procYm;
	}

	public String getNpWithLip() {
		return npWithLip;
	}

	public void setNpWithLip(String npWithLip) {
		this.npWithLip = npWithLip;
	}

	public String getDemDate() {
		return demDate;
	}

	public void setDemDate(String demDate) {
		this.demDate = demDate;
	}

}
