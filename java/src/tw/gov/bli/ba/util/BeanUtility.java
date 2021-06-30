package tw.gov.bli.ba.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanUtility {
    private static Log log = LogFactory.getLog(BeanUtility.class);

    /**
     * 取指定的 Bean Property 值<br>
     * 
     * @param bean 欲取得值的 Bena
     * @param propertyName 欲取得值的 Property 名稱
     * @return 欲取得的 Property 值, 如果發生錯誤則傳回 null
     */
    public static Object getBeanProperty(Object bean, String propertyName) {
        try {
            if (PropertyUtils.isReadable(bean, propertyName)) {
                return PropertyUtils.getSimpleProperty(bean, propertyName);
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            log.debug("執行 BeanUtility.getBeanProperty() 失敗 Bean Class: " + bean.getClass().getName() + " Property Name: " + propertyName);
            return null;
        }
    }

    /**
     * 將 Bean Property 的值 設定為所指定的 value 值<br>
     * 
     * @param bean 欲寫入的 Bean
     * @param propertyName 欲寫入的 Property 名稱
     * @param value 欲寫入的值
     */
    public static void setBeanProperty(Object bean, String propertyName, Object value) {
        try {
            // 檢查 Bean Property 是否可寫入
            if (PropertyUtils.isWriteable(bean, propertyName)) {
                BeanUtils.copyProperty(bean, propertyName, value);
            }
        }
        catch (Exception e) {
            log.debug("執行 BeanUtility.setBeanProperty() 失敗 Bean Class: " + bean.getClass().getName() + " Property Name: " + propertyName);
        }
    }

    /**
     * 複製傳入的 Bean 成為一個新的 Bean Instance<br>
     * 只是包裝 BeanUtils.cloneBean(), 讓呼叫端不用去處理例外...
     * 
     * @param bean
     * @return
     */
    public static Object cloneBean(Object bean) {
        try {
            return BeanUtils.cloneBean(bean);
        }
        catch (Exception e) {
            log.debug("BeanUtility.cloneBean() 複製 Bean 時發生錯誤:" + bean.getClass().getName());
            return null;
        }
    }

    /**
     * 拷貝 Bean, Map 的值...<br>
     * 只是包裝 BeanUtils.copyProperties(), 讓呼叫端不用去處理例外...
     * 
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        }
        catch (Exception e) {
            log.debug("BeanUtility.copyProperties() 複製資料時發生錯誤: from " + orig.getClass().getName() + " to " + dest.getClass().getName());
        }
    }

    /**
     * 拷貝 Bean 的值, 預定 null 會換成適當的初始值...<br>
     * 只是個方便的方法, 讓呼叫端不用去處理例外...
     * 
     * @param obj
     * @param orig
     */
    public static void copyPropertiesNonNull(Object dest, Object orig) {
        try {
            copyPropertiesWithoutNull(dest, orig);
        }
        catch (Exception e) {
            log.debug("BeanUtility.copyPropertiesNonNull() 複製資料時發生錯誤: from " + orig.getClass().getName() + " to " + dest.getClass().getName());
        }
    }

    /**
     * 將 Bean 的 null 值取代成適當的初始值...<br>
     * 只是個方便的方法, 讓呼叫端不用去處理例外...
     * 
     * @param obj
     */
    public static void replaceNullProperties(Object obj) {
        try {
            replacePropertiesWithoutNull(obj);
        }
        catch (Exception e) {
            log.debug("BeanUtility.replaceNullProperties() 取代空值時發生錯誤: from " + obj.getClass().getName());
        }
    }

    /**
     * 將 Bean 欄位的值設回初始值
     * 
     * @param obj
     */
    public static void resetProperties(Object obj) {
        try {
            resetPropertiesValue(obj);
        }
        catch (Exception e) {
            log.debug("BeanUtility.resetProperties() 發生錯誤: from " + obj.getClass().getName());
        }
    }

    /**
     * 將傳入的 Standard JavaBean 內的 Properties 設回初始值<br>
     * (沒寫所有的型態, 有用到自己加...)
     * 
     * @param obj Object Standard JavaBean
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void resetPropertiesValue(java.lang.Object obj) throws IllegalAccessException, InvocationTargetException {
        if (obj == null)
            throw new IllegalArgumentException("No origin bean specified");

        PropertyDescriptor objDescriptors[] = PropertyUtils.getPropertyDescriptors(obj);

        for (int i = 0; i < objDescriptors.length; i++) { // ... [
            String name = objDescriptors[i].getName();
            if ("class".equals(name))
                continue; // No point in trying to set an object's class

            if (PropertyUtils.isReadable(obj, name) && PropertyUtils.isWriteable(obj, name)) {
                try {
                    Object value = null;

                    if (value == null) {
                        if (objDescriptors[i].getPropertyType().getName().equals("java.lang.Boolean"))
                            value = new java.lang.Boolean(false);
                    }
                    BeanUtils.setProperty(obj, name, value);
                }
                catch (Exception e) {
                    ; // Should not happen
                }
            }
        } // ] ... end for (int i = 0; i < objDescriptors.length; i++)
    }

    /**
     * 將傳入的 Standard JavaBean 內的 Properties 遇到 null 的依其型態換成適當的值<br>
     * (沒寫所有的型態, 有用到自己加...)
     * 
     * @param obj Object Standard JavaBean
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void replacePropertiesWithoutNull(java.lang.Object obj) throws IllegalAccessException, InvocationTargetException {
        if (obj == null)
            throw new IllegalArgumentException("No origin bean specified");

        PropertyDescriptor objDescriptors[] = PropertyUtils.getPropertyDescriptors(obj);

        for (int i = 0; i < objDescriptors.length; i++) { // ... [
            String name = objDescriptors[i].getName();
            if ("class".equals(name))
                continue; // No point in trying to set an object's class

            if (PropertyUtils.isReadable(obj, name) && PropertyUtils.isWriteable(obj, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(obj, name);

                    if (value == null) {
                        if (objDescriptors[i].getPropertyType().getName().equals("java.lang.String"))
                            value = "";
                        else if (objDescriptors[i].getPropertyType().getName().equals("java.lang.Double"))
                            value = new Double(0.0d);
                        else if (objDescriptors[i].getPropertyType().getName().equals("java.lang.Float"))
                            value = new Float(0.0);
                        else if (objDescriptors[i].getPropertyType().getName().equals("java.lang.Long"))
                            value = new Long(0l);
                        else if (objDescriptors[i].getPropertyType().getName().equals("java.lang.Integer"))
                            value = new Integer(0);
                        else if (objDescriptors[i].getPropertyType().getName().equals("java.math.BigDecimal"))
                            value = new BigDecimal(0);
                    }
                    BeanUtils.copyProperty(obj, name, value);
                }
                catch (NoSuchMethodException e) {
                    ; // Should not happen
                }
            }
        } // ] ... end for (int i = 0; i < objDescriptors.length; i++)
    }

    /**
     * 將傳入的 Standard JavaBean 內的 Properties 值拷貝到另一個 Standard JavaBean,<br>
     * 拷貝的原則是只要來源的 Property 有提供 Get 方法, 且在目的有提供 set 方法即可...<br>
     * 遇到 null 的依其型態換成適當的值...<br>
     * 兩個 JavaBean 不須是同一種 Class...
     * 
     * @param dest Object 目的 Standard JavaBean
     * @param orig Object 來源 Standard JavaBean
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyPropertiesWithoutNull(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
        // Validate existence of the specified beans
        if (dest == null)
            throw new IllegalArgumentException("No destination bean specified");

        if (orig == null)
            throw new IllegalArgumentException("No origin bean specified");

        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);

        for (int i = 0; i < origDescriptors.length; i++) { // ... [
            String name = origDescriptors[i].getName();
            if ("class".equals(name))
                continue; // No point in trying to set an object's class

            if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(orig, name);
                    BeanUtils.copyProperty(dest, name, value);
                    replaceNullProperties(dest);
                }
                catch (NoSuchMethodException e) {
                    ; // Should not happen
                }
            }
        } // ] ... end for (int i = 0; i < origDescriptors.length; i++)
    }
    
    /**
     * 拷貝 Bean, Map 的值...<br>
     * 此方法會順便將 Property 的值拷貝到有 oldFieldPrefix 開頭的 Property<br>
     * 例如: <br>
     * Bean 有一個 Property abc 的值為 "123", oldFieldPrefix 為 "old"<br>
     * 若 Bean 有另一個叫 oldAbc 的 Property, 則會將 abc 的值拷貝給 oldAbc, 此時 oldAbc 的值亦為 "123"
     * 
     * @param dest
     * @param orig
     * @param oldFieldPrefix 用來存放欄位修改前值的 Property 名稱 Prefix
     */
    public static void copyPropertiesForUpdate(Object dest, Object orig, String oldFieldPrefix) {
        try {
            BeanUtils.copyProperties(dest, orig);

            PropertyDescriptor destDescriptors[] = PropertyUtils.getPropertyDescriptors(dest);
            for (int i = 0; i < destDescriptors.length; i++) {
                String name = destDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }

                // oldFieldPrefix 開頭的屬性不用做
                if (StringUtils.indexOf(name, oldFieldPrefix) != -1 && StringUtils.indexOf(name, oldFieldPrefix) == 0) {
                    continue;
                }

                String compareName = oldFieldPrefix + StringUtils.upperCase(name.substring(0, 1)) + name.substring(1, name.length());

                PropertyDescriptor compareDescriptor = null;
                try {
                    compareDescriptor = PropertyUtils.getPropertyDescriptor(dest, compareName);
                    if (compareDescriptor == null)
                        continue;
                }
                catch (Exception e) {
                    continue;
                }

                if (PropertyUtils.isReadable(dest, name) && PropertyUtils.isWriteable(dest, compareName)) {
                    try {
                        Object value = PropertyUtils.getSimpleProperty(dest, name);
                        BeanUtils.copyProperty(dest, compareName, value);
                    }
                    catch (NoSuchMethodException e) {
                        ; // Should not happen
                    }
                }
            }
        }
        catch (Exception e) {
            log.debug("BeanUtility.copyPropertiesForUpdate() 複製資料時發生錯誤: from " + orig.getClass().getName() + " to " + dest.getClass().getName());
        }
    }
}
