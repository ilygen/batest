package tw.gov.bli.ba.maint.forms;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.util.DateUtility;

/**
 * 維護作業 - 年金受理資料轉出
 * 
 * @author Eddie
 */
public class AnnuityAcceptDataTransferForm extends BaseValidatorForm {

    private String apNo; //受理編號

	public String getApNo() {
		return apNo;
	}

	public void setApNo(String apNo) {
		this.apNo = apNo;
	}

}
