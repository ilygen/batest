package tw.gov.bli.ba.rpt.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 決行作業相關報表 - 歸檔清單點交清冊 - 查詢頁面 (balp0d220l.jsp)
 * 
 * @author Goston
 */
public class DecisionRpt02Form extends BaseValidatorForm {
    private String method;

    private String payCode; // 給付別
    private String decisionDate; // 歸檔日期
    private BigDecimal pageBegin;// 歸檔頁次起
    private BigDecimal pageEnd;// 歸檔頁次迄
    private String exeMan; // 決行人員

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

    public String getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

    public BigDecimal getPageBegin() {
        return pageBegin;
    }

    public void setPageBegin(BigDecimal pageBegin) {
        this.pageBegin = pageBegin;
    }

    public BigDecimal getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(BigDecimal pageEnd) {
        this.pageEnd = pageEnd;
    }

	public String getExeMan() {
		return exeMan;
	}

	public void setExeMan(String exeMan) {
		this.exeMan = exeMan;
	}

}
