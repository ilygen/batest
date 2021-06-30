package tw.gov.bli.ba.helper;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.Global;
import tw.gov.bli.ba.domain.Baapplog;

public class LoggingHelper {
    protected static ResourceBundle bundle = ResourceBundle.getBundle(Global.BA_RESOURCE_BUNDLE);

    /**
     * 判斷 Bean 中有哪些屬性修改過並傳回內含 Baapplog 物件的 List<br>
     * 比較方式:<br>
     * 假設 Bean 中有個 property 叫 abc 則檢查是否存在一個叫 oldAbc 的 property,<br>
     * 若存在則比對 abc 及 oldAbc 的值, 若比對值相異則新增一個 Baapplog 物件進 List 中<br>
     * 
     * @param obj 通常為 Form 物件
     * @param oldFieldPrefix 用來存放欄位修改前值的 Property 名稱 Prefix
     * @param ResourceBundlePrefix ResourceBundle 中欄位的 Key Prefix
     * @return
     */
    public static List<Baapplog> getBaapplogList(Object obj, String oldFieldPrefix, String ResourceBundlePrefix) {
        List<Baapplog> list = new ArrayList<Baapplog>();

        if (obj != null) { // begin ... [
            PropertyDescriptor objDescriptors[] = PropertyUtils.getPropertyDescriptors(obj);

            for (int i = 0; i < objDescriptors.length; i++) { // begin ... [
                String name = objDescriptors[i].getName();

                if ("class".equals(name)) {
                    continue;
                }

                // old 開頭的屬性不用做
                if (StringUtils.indexOf(name, oldFieldPrefix) != -1 && StringUtils.indexOf(name, oldFieldPrefix) == 0) {
                    continue;
                }

                String compareName = oldFieldPrefix + StringUtils.upperCase(name.substring(0, 1)) + name.substring(1, name.length());
                PropertyDescriptor compareDescriptor = null;
                try {
                    compareDescriptor = PropertyUtils.getPropertyDescriptor(obj, compareName);
                    if (compareDescriptor == null)
                        continue;
                }
                catch (Exception e) {
                    continue;
                }

                if (PropertyUtils.isReadable(obj, name) && PropertyUtils.isReadable(obj, compareName)) {
                    try {
                        Object value = PropertyUtils.getSimpleProperty(obj, name);
                        Object compareValue = PropertyUtils.getSimpleProperty(obj, compareName);

                        Converter converter = ConvertUtils.lookup(objDescriptors[i].getPropertyType().getComponentType());
                        if (converter != null) {
                            compareValue = converter.convert(objDescriptors[i].getPropertyType(), compareValue);
                        }

                        // 值不相同
                        if (!StringUtils.equals(StringUtils.defaultString(ConvertUtils.convert(value)), StringUtils.defaultString(ConvertUtils.convert(compareValue)))) {
                            Baapplog baapplog = new Baapplog();
                            // 從 ResourceBundle 取得欄位名稱
                            baapplog.setUpCol(StringUtils.defaultString(bundle.getString(ResourceBundlePrefix + name))); // 異動項目
                            baapplog.setBvalue(StringUtils.defaultString(ConvertUtils.convert(compareValue))); // 改前內容
                            baapplog.setAvalue(StringUtils.defaultString(ConvertUtils.convert(value))); // 改後內容
                            list.add(baapplog);
                        }
                    }
                    catch (Exception e) {
                        ; // Should not happen
                    }
                }
            } // ] ... end for (int i = 0; i < objDescriptors.length; i++) {
        } // ] ... end if (obj != null)

        return list;
    }

}
