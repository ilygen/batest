package tw.gov.bli.ba.framework.struts.plugin;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang.StringUtils;
import java.math.BigDecimal;

public final class BigDecimalConverter implements Converter {

    public BigDecimalConverter() {
        this.defaultValue = null;
        this.useDefault = false;
    }

    public BigDecimalConverter(Object defaultValue) {
        this.defaultValue = defaultValue;
        this.useDefault = true;
    }

    private Object defaultValue = null;

    private boolean useDefault = true;

    @SuppressWarnings("unchecked")
    public Object convert(Class type, Object value) {
        if (value == null || "".equals(value)) {
            if (useDefault)
                return (defaultValue);
            else
                return null;
        }

        if (value instanceof BigDecimal)
            return (value);

        try {
            if (isString(value)) {
                return (new BigDecimal(StringUtils.remove(value.toString(), ","))); // 移除千分號
            }
            return (new BigDecimal(value.toString()));
        }
        catch (Exception e) {
            if (useDefault)
                return (defaultValue);
            else
                return null;
        }
    }

    protected static boolean isString(Object o) {
        return (o == null) ? true : String.class.isInstance(o);
    }
}
