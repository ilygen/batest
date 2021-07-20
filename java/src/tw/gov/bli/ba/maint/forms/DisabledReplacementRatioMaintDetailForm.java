package tw.gov.bli.ba.maint.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class DisabledReplacementRatioMaintDetailForm extends BaseValidatorForm {
    private String method;
    
	private String effYm;            // 施行年月
	private BigDecimal insAvg1;      // 平均薪資級距1
	private BigDecimal insAvg2;      // 平均薪資級距2
    private BigDecimal insAvg3;      // 平均薪資級距3
    private BigDecimal insAvg4;      // 平均薪資級距4
    private BigDecimal irrTypeA2;    // 所得替代率A
    private BigDecimal irrTypeB2;    // 所得替代率B
    private BigDecimal irrTypeC2;    // 所得替代率C
    private BigDecimal irrTypeD2;    // 所得替代率D
    private String effYmOrg;       // 原查詢的施行年度
    private String user;     // 修改人員
    private String date;     // 修改日期
    //private BigDecimal irrTypeA1;    // 所得替代率第一式A1
    //private BigDecimal irrTypeB1;    // 所得替代率第一式B1
    //private BigDecimal irrTypeC1;    // 所得替代率第一式C1
    //private BigDecimal irrTypeD1;    // 所得替代率第一式D1

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
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

	public String getEffYm() {
		return effYm;
	}

	public void setEffYm(String effYm) {
		this.effYm = effYm;
	}

	public BigDecimal getInsAvg1() {
		return insAvg1;
	}

	public void setInsAvg1(BigDecimal insAvg1) {
		this.insAvg1 = insAvg1;
	}

	public BigDecimal getInsAvg2() {
		return insAvg2;
	}

	public void setInsAvg2(BigDecimal insAvg2) {
		this.insAvg2 = insAvg2;
	}

	public BigDecimal getInsAvg3() {
		return insAvg3;
	}

	public void setInsAvg3(BigDecimal insAvg3) {
		this.insAvg3 = insAvg3;
	}

	public BigDecimal getInsAvg4() {
		return insAvg4;
	}

	public void setInsAvg4(BigDecimal insAvg4) {
		this.insAvg4 = insAvg4;
	}

	public BigDecimal getIrrTypeA2() {
		return irrTypeA2;
	}

	public void setIrrTypeA2(BigDecimal irrTypeA2) {
		this.irrTypeA2 = irrTypeA2;
	}

	public BigDecimal getIrrTypeB2() {
		return irrTypeB2;
	}

	public void setIrrTypeB2(BigDecimal irrTypeB2) {
		this.irrTypeB2 = irrTypeB2;
	}

	public BigDecimal getIrrTypeC2() {
		return irrTypeC2;
	}

	public void setIrrTypeC2(BigDecimal irrTypeC2) {
		this.irrTypeC2 = irrTypeC2;
	}

	public BigDecimal getIrrTypeD2() {
		return irrTypeD2;
	}

	public void setIrrTypeD2(BigDecimal irrTypeD2) {
		this.irrTypeD2 = irrTypeD2;
	}

	public String getEffYmOrg() {
		return effYmOrg;
	}

	public void setEffYmOrg(String effYmOrg) {
		this.effYmOrg = effYmOrg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
