package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 決行作業相關報表 - 歸檔清單 - 查詢頁面 (balp0d210l.jsp)
 * 
 * @author Goston
 */
public class DecisionRpt01Form extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String decisionSdate;// 決行日期起
    private String decisionEdate;// 決行日期迄
    private String exeMan; //決行人員

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

    public String getDecisionSdate() {
        return decisionSdate;
    }

    public void setDecisionSdate(String decisionSdate) {
        this.decisionSdate = decisionSdate;
    }

    public String getDecisionEdate() {
        return decisionEdate;
    }

    public void setDecisionEdate(String decisionEdate) {
        this.decisionEdate = decisionEdate;
    }

	public String getExeMan() {
		return exeMan;
	}

	public void setExeMan(String exeMan) {
		this.exeMan = exeMan;
	}
    

}
