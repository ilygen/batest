package tw.gov.bli.ba.bj.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 批次處理 - 補正核付資料作業 - 補正核付作業
 * 
 * @author Eddie
 */
public class CompPaymentForm extends BaseValidatorForm {

    private String apNo1;
    private String apNo2;
    private String apNo3;
    private String apNo4;
    private String chkDate;
    private String aplPayDate;
    private String remitDate;
    private String mtestMk;
    private BigDecimal baappbaseId;
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        if (page != 1)
            return null;

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }
    
	public String getApNo1() {
		return apNo1;
	}
	public void setApNo1(String apNo1) {
		this.apNo1 = apNo1;
	}
	public String getApNo2() {
		return apNo2;
	}
	public void setApNo2(String apNo2) {
		this.apNo2 = apNo2;
	}
	public String getApNo3() {
		return apNo3;
	}
	public void setApNo3(String apNo3) {
		this.apNo3 = apNo3;
	}
	public String getApNo4() {
		return apNo4;
	}
	public void setApNo4(String apNo4) {
		this.apNo4 = apNo4;
	}
	public String getChkDate() {
		return chkDate;
	}
	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}
	public String getAplPayDate() {
		return aplPayDate;
	}
	public void setAplPayDate(String aplPayDate) {
		this.aplPayDate = aplPayDate;
	}
	public String getRemitDate() {
		return remitDate;
	}
	public void setRemitDate(String remitDate) {
		this.remitDate = remitDate;
	}
	public String getMtestMk() {
		return mtestMk;
	}
	public void setMtestMk(String mtestMk) {
		this.mtestMk = mtestMk;
	}
	public BigDecimal getBaappbaseId() {
		return baappbaseId;
	}
	public void setBaappbaseId(BigDecimal baappbaseId) {
		this.baappbaseId = baappbaseId;
	}
}
