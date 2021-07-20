package tw.gov.bli.ba.framework.struts.validator;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;
import tw.gov.bli.ba.util.ValidateUtility;

public class IdNoValidator implements Serializable {
    private static final Log log = LogFactory.getLog(IdNoValidator.class);

    /**
     * 驗證身分證字號是否正確
     * 
     * @param bean
     * @param va
     * @param field
     * @param errors
     * @param validator
     * @param request
     * @return
     */
    public static boolean validateIdNo(Object bean, ValidatorAction va, Field field, ActionMessages errors, Validator validator, HttpServletRequest request) {
        boolean result = false;

        String value = null;

        value = evaluateBean(bean, field);

        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        try {
            result = ValidateUtility.validatePersonIdNo(value);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (!result) {
            errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
        }

        return result;
    }

    private static String evaluateBean(Object bean, Field field) {
        String value;

        if (isString(bean)) {
            value = (String) bean;
        }
        else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        return value;
    }

    protected static boolean isString(Object o) {
        return (o == null) ? true : String.class.isInstance(o);
    }
}
