package tw.gov.bli.ba.update.forms;

import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 更正作業 - 眷屬資料更正 (BAMO0D072C)
 * 
 * @author Evelyn Hsu
 */

public class DependantDataUpdateListForm extends BaseValidatorForm {
    
    private String method;
    
    
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
