package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Action Form: 遺屬年金受款人資料更正 - 重殘起訖年月維護作業
 *
 * 
 * 
 * 
 */
public class SurvivorPayeeDataUpdateHandicapTermForm extends BaseValidatorForm {

	private String apNo;// 受理編號
	private String seqNo;// 受款人序號
	private String termNo;// 序號
	private String handicapSdate;// 重殘起始年月
	private String handicapEdate;// 重殘結束年月

	public String getApNo() {
		return apNo;
	}

	public void setApNo(String apNo) {
		this.apNo = apNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getHandicapSdate() {
		return handicapSdate;
	}

	public void setHandicapSdate(String handicapSdate) {
		this.handicapSdate = handicapSdate;
	}

	public String getHandicapEdate() {
		return handicapEdate;
	}

	public void setHandicapEdate(String handicapEdate) {
		this.handicapEdate = handicapEdate;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.apNo = "";
		this.seqNo = "";
		this.termNo = "";
		this.handicapSdate = "";
		this.handicapEdate = "";
	}
}
