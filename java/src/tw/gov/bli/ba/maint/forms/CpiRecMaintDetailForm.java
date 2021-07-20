package tw.gov.bli.ba.maint.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class CpiRecMaintDetailForm extends BaseValidatorForm {
    private String method;

    private String issuYear;    // 調整年度
    private String adjYmB;     // 調整起月
    private String reqRpno;    // 報請核定文號
    private String issuRpno;   // 核定文號
    private String issuDesc;   // 核定結果
    private String crtUser;    // 員工編號
    private String crtTime;    // 系統日期時間
    private String sysDate;    // 系統日期
    private String lastAdjYmB; //最後一筆資料之調整起月
    private String user;       //修改人員
    private String date;       //修改日期

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

    public String getIssuYear() {
		return issuYear;
	}

	public void setIssuYear(String issuYear) {
		this.issuYear = issuYear;
	}

	public String getAdjYmB() {
        return adjYmB;
    }

    public void setAdjYmB(String adjYmB) {
        this.adjYmB = adjYmB;
    }

    public String getReqRpno() {
        return reqRpno;
    }

    public void setReqRpno(String reqRpno) {
        this.reqRpno = reqRpno;
    }

    public String getIssuRpno() {
        return issuRpno;
    }

    public void setIssuRpno(String issuRpno) {
        this.issuRpno = issuRpno;
    }

    public String getIssuDesc() {
        return issuDesc;
    }

    public void setIssuDesc(String issuDesc) {
        this.issuDesc = issuDesc;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }
    
    public String getSysDate() {
        return sysDate;
    }
    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

	public String getLastAdjYmB() {
		return lastAdjYmB;
	}

	public void setLastAdjYmB(String lastAdjYmB) {
		this.lastAdjYmB = lastAdjYmB;
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
