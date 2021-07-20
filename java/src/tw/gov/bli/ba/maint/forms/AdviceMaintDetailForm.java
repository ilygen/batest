package tw.gov.bli.ba.maint.forms;

/**
 * 勞工保險年金給付系統 - 核定通知書查詢
 * 查詢條件輸入頁面
 * bapa0x021a.jsp
 */
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class AdviceMaintDetailForm extends BaseValidatorForm {
    private String method;
    private String type;
    
    private String payCode; // 給付別
    private String authTyp; // 核定格式
    private String actMk; // 格式是否有效
    private String dataContPurpose; // 主旨
    private String dataContExplain; // 說明
    private String chooseData; // 判斷選擇主旨或說明 1主旨 2說明
    private String addCode; //插入代碼
    private String addMode; //從新增或修改插入 A新增 U修改
    private String cursorPurposePosition; //主旨滑鼠在文字中位置
    private String cursorExplainPosition; //主旨滑鼠在文字中位置
    private String authTypDesc; // 格式說明
    private String crtUser; //新增人員
    private String crtTime; //新增日期

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

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getAuthTyp() {
        return authTyp;
    }

    public void setAuthTyp(String authTyp) {
        this.authTyp = authTyp;
    }

    public String getDataContPurpose() {
        return dataContPurpose;
    }

    public void setDataContPurpose(String dataContPurpose) {
        this.dataContPurpose = dataContPurpose;
    }

    public String getDataContExplain() {
        return dataContExplain;
    }

    public void setDataContExplain(String dataContExplain) {
        this.dataContExplain = dataContExplain;
    }

    public String getActMk() {
        return actMk;
    }

    public void setActMk(String actMk) {
        this.actMk = actMk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getChooseData() {
		return chooseData;
	}

	public void setChooseData(String chooseData) {
		this.chooseData = chooseData;
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public String getAddMode() {
		return addMode;
	}

	public void setAddMode(String addMode) {
		this.addMode = addMode;
	}

	public String getCursorPurposePosition() {
		return cursorPurposePosition;
	}

	public void setCursorPurposePosition(String cursorPurposePosition) {
		this.cursorPurposePosition = cursorPurposePosition;
	}

	public String getCursorExplainPosition() {
		return cursorExplainPosition;
	}

	public void setCursorExplainPosition(String cursorExplainPosition) {
		this.cursorExplainPosition = cursorExplainPosition;
	}

	public String getAuthTypDesc() {
		return authTypDesc;
	}

	public void setAuthTypDesc(String authTypDesc) {
		this.authTypDesc = authTypDesc;
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

}