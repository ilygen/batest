package tw.gov.bli.ba.executive.forms;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

public class ExecutiveSupportCloseListForm extends BaseValidatorForm {
    private BigDecimal[] checkBox;
   
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = super.validate(mapping, request);

        // Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
        if (errors != null && errors.size() > 0)
            return errors;

        return errors;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        checkBox = null;
    }

    public BigDecimal[] getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(BigDecimal[] checkBox) {
        this.checkBox = checkBox;
    }

}
