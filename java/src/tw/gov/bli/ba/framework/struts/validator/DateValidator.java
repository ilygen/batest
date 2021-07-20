package tw.gov.bli.ba.framework.struts.validator;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;
import tw.gov.bli.ba.util.DateUtility;

public class DateValidator implements Serializable {
    private static final Log log = LogFactory.getLog(DateValidator.class);

    /**
     * 驗證日期是否 "不" 大於 今天
     * 
     * @param bean
     * @param va
     * @param field
     * @param errors
     * @param validator
     * @param request
     * @return 日期小於等於今天則傳回 true, 大於今天則傳回 false
     */
    public static boolean validateNotAfterToday(Object bean, ValidatorAction va, Field field, ActionMessages errors, Validator validator, HttpServletRequest request) {
        boolean result = false;

        String value = null;

        value = evaluateBean(bean, field);

        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        try {
            if (Integer.parseInt(value) <= Integer.parseInt(DateUtility.getNowChineseDate()))
                result = true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (!result) {
            errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
        }

        return result;
    }

    /**
     * 驗證七碼民國日期是否正確
     * 
     * @param bean
     * @param va
     * @param field
     * @param errors
     * @param validator
     * @param request
     * @return
     */
    public static boolean validateChineseDate(Object bean, ValidatorAction va, Field field, ActionMessages errors, Validator validator, HttpServletRequest request) {
        boolean result = false;
        boolean bBeforeROC = false;

        String value = null;

        value = evaluateBean(bean, field);

        String sROCFieldName = field.getVarValue("ROCFieldName");
        String sRocFieldValue = "";

        if (sROCFieldName != null)
            sRocFieldValue = ValidatorUtils.getValueAsString(bean, sROCFieldName);

        if (StringUtils.upperCase(StringUtils.defaultString(sRocFieldValue)).equals("TRUE")) {
            bBeforeROC = true;
        }

        if (GenericValidator.isBlankOrNull(value)) {
            return true;
        }

        try {
            if (Integer.parseInt(value) < 0) {
                bBeforeROC = true;
            }
            
            result = DateUtility.isValidDate(value.replace("-", ""), bBeforeROC);
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
