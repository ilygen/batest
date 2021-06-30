package tw.gov.bli.ba.update.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 眷屬資料更正 (BAMO0D090C)
 * 
 * @author Noctis
 */

public class OldAgeDeathRepayForm extends BaseValidatorForm {
    
    private String method;
    
    private String apNo1; // 受理編號 - 第一欄 ( 1碼 )
    private String apNo2; // 受理編號 - 第二欄 ( 1碼 )
    private String apNo3; // 受理編號 - 第三欄 ( 5碼 )
    private String apNo4; // 受理編號 - 第四欄 ( 5碼 )
    private String seqNo; // 受款人序
    private int[] index;// 資料列序號
    private String checkQryData4;//檢核QryData4(退匯金額分配資料)是否有資料 無=0 有=1
    
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }
    
    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public int[] getIndex() {
        return index;
    }
    public void setIndex(int[] index) {
        this.index = index;
    }
	public String getCheckQryData4() {
		return checkQryData4;
	}
	public void setCheckQryData4(String checkQryData4) {
		this.checkQryData4 = checkQryData4;
	}
	
	

}
