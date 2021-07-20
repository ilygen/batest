package tw.gov.bli.ba.rpt.forms;


import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 其他類報表 - 轉呆帳核定清單 - 查詢頁面 (balp0d680l.jsp)
 * 
 * @author ZeHua
 */
public class OtherRpt08Form extends BaseValidatorForm {
	private String method;

    private String payCode; // 給付別
    private String deadYy; // 呆帳年度
    private String apNo1;//受理編號1
    private String apNo2;//受理編號2
    private String apNo3;//受理編號3
    private String apNo4;//受理編號4
	private String apNoOfConfirm;
	

    private String bDebtDate;//轉呆帳日期
    
    
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
        deadYy = "";
        apNo1="";
        apNo2="";
        apNo3="";
        apNo4="";
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
	public String getDeadYy() {
		return deadYy;
	}

	public void setDeadYy(String deadYy) {
		this.deadYy = deadYy;
	}

	public String getApNoOfConfirm() {
		return apNoOfConfirm;
	}

	public void setApNoOfConfirm(String apNoOfConfirm) {
		this.apNoOfConfirm = apNoOfConfirm;
	}

	public String getbDebtDate() {
		return bDebtDate;
	}

	public void setbDebtDate(String bDebtDate) {
		this.bDebtDate = bDebtDate;
	}
    
}
