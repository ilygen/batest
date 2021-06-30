package tw.gov.bli.ba.framework.struts.forms;

import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.validator.ValidatorForm;
import tw.gov.bli.ba.Global;

/**
 * 系統 Base Form, 所有的 Form 均繼承此 Class
 * 
 * @author Goston
 */
public class BaseValidatorForm extends ValidatorForm {
    private static Log log = LogFactory.getLog(BaseValidatorForm.class);

    protected ResourceBundle resourceBundle = ResourceBundle.getBundle(Global.BA_RESOURCE_BUNDLE);
}
