package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 受款人資料更正 - 失能年金受款人資料更正(BAMO0D182C)
 * @author Azuritul
 *
 */
public class DisabledPayeeDataUpdateListForm extends BaseValidatorForm {
	private String method;
	//private String mustIssueAmt[];
	//private String oldMustIssueAmt[];

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

//	public String[] getMustIssueAmt() {
//		return mustIssueAmt;
//	}
//
//	public void setMustIssueAmt(String[] mustIssueAmt) {
//		this.mustIssueAmt = mustIssueAmt;
//	}
//
//	public String[] getOldMustIssueAmt() {
//		return oldMustIssueAmt;
//	}
//
//	public void setOldMustIssueAmt(String[] oldMustIssueAmt) {
//		this.oldMustIssueAmt = oldMustIssueAmt;
//	}	

}
