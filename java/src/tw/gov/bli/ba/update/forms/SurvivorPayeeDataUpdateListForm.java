package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 受款人資料更正 - 遺屬年金受款人資料更正(BAMO0D282C)
 * @author Azuritul
 *
 */
public class SurvivorPayeeDataUpdateListForm extends BaseValidatorForm  {
	
	private String method;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
