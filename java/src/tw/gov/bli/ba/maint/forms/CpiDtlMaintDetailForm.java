package tw.gov.bli.ba.maint.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class CpiDtlMaintDetailForm extends BaseValidatorForm {
    private String method;
    
    private String adjYear;    // 核定年度
    private String cpiYear;    // 指數年度（起）
    private String cpiYearE;   // 指數年度（迄）
    private BigDecimal cpiB;  // 物價指數一
    private BigDecimal cpiE;  // 物價指數二
    private String adjMk; // 調整註記
    private BigDecimal cpIndex; // 累計成長率
    private String reqRpno;    // 報請核定文號
    private String issuRpno;   // 核定文號
    private String issuDesc;   // 核定結果
    private String crtUser;    // 員工編號
    private String crtTime;    // 系統日期時間
    private String sysDate;    // 系統日期
    private String user;       //修改人員
    private String date;       //修改日期
    private String issuYear; // 指數年度 起
    private String adjYearS; // 申請年度(起)
    private String adjYearE; // 申請年度(迄)

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
    
    public String getCpiYear() {
        return cpiYear;
    }
        
    public void setCpiYear(String cpiYear) {
        this.cpiYear = cpiYear;
    }    
    
    public BigDecimal getCpIndex() {
        return cpIndex;
    }

    public void setCpIndex(BigDecimal cpIndex) {
        this.cpIndex = cpIndex;
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
    
    
    public String getAdjYear() {
        return adjYear;
    }
        
    public void setAdjYear(String adjYear) {
        this.adjYear = adjYear;
    }

	public String getCpiYearE() {
		return cpiYearE;
	}

	public void setCpiYearE(String cpiYearE) {
		this.cpiYearE = cpiYearE;
	}

	public BigDecimal getCpiB() {
		return cpiB;
	}

	public void setCpiB(BigDecimal cpiB) {
		this.cpiB = cpiB;
	}

	public BigDecimal getCpiE() {
		return cpiE;
	}

	public void setCpiE(BigDecimal cpiE) {
		this.cpiE = cpiE;
	}

	public String getSysDate() {
		return sysDate;
	}

	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	public String getAdjMk() {
		return adjMk;
	}

	public void setAdjMk(String adjMk) {
		this.adjMk = adjMk;
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

	public String getIssuYear() {
		return issuYear;
	}

	public void setIssuYear(String issuYear) {
		this.issuYear = issuYear;
	}

	public String getAdjYearS() {
		return adjYearS;
	}

	public void setAdjYearS(String adjYearS) {
		this.adjYearS = adjYearS;
	}

	public String getAdjYearE() {
		return adjYearE;
	}

	public void setAdjYearE(String adjYearE) {
		this.adjYearE = adjYearE;
	}
    
    
}
