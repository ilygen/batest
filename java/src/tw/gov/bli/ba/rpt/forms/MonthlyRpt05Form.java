package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
/**
 * 列印作業 - 月核定處理相關報表 - 給付函核定通知書  (balp0d350l.jsp)
 * 
 * @author jerry
 */
public class MonthlyRpt05Form extends BaseValidatorForm {
    private String method;
    
    private String payCode; // 給付別
    private String issuYm; // 核定年月
    private String apNo1;//受理編號
    private String apNo2;//受理編號
    private String apNo3;//受理編號
    private String apNo4;//受理編號
    private String proDate;//處理日期

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


    public String getIssuYm() {
        return issuYm;
    }


    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
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

    public String getProDate() {
        return proDate;
    }


    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

}