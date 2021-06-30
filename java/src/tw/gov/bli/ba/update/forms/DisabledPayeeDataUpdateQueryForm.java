package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 失能年金受款人資料更正 (BAMO0D180C) 查詢
 * @author Azuritul
 *
 */
public class DisabledPayeeDataUpdateQueryForm extends BaseValidatorForm {
	private String method;

    private String apNo1; // 受理編號-1
    private String apNo2; // 受理編號-2
    private String apNo3; // 受理編號-3
    private String apNo4; // 受理編號-4

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
    
    public String getApNo(){
    	if(apNo1 == null) apNo1 = "";
    	if(apNo2 == null) apNo2 = "";
    	if(apNo3 == null) apNo3 = "";
    	if(apNo4 == null) apNo4 = "";
    	return new StringBuilder(apNo1).append(apNo2).append(apNo3).append(apNo4).toString();
    }
}
