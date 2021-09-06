package tw.gov.bli.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.ConstantKey;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;
import tw.gov.bli.common.domain.Mmaplog;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.services.LoggingService;

/**
 * Framework 提供用的 Log Utility Class, 未使用 Framework 之 Log 機制請勿叫用
 * 
 * @author Goston
 */
public class FrameworkLogUtil {
    private static Log log = LogFactory.getLog(FrameworkLogUtil.class);

    /**
     * Portal Log
     * 
     * @param request
     */
    public static void doPortalLog(HttpServletRequest request) {
        try {
            LoggingService loggingService = (LoggingService) SpringHelper.getBeanById(ConstantKey.LOGGING_SERVICE_ID);

            // 檢核是否需紀錄 Log
            if (EnvFacadeHelper.isNeedLogging(request)) {
                UserInfo userData = UserSessionHelper.getUserData(request);

                // 使用者沒登入
                if (userData == null)
                    return;

                String path = request.getServletPath();

                // 取得使用者之前所執行的 應用系統功能代號
                String previousApFunctionCode = StringUtils.defaultString(userData.getApFunctionCode());

                // 取得使用者本次 Request 所執行的 應用系統功能代號 及 名稱
                String currentApFunctionCode = StringUtils.defaultString(EnvFacadeHelper.getItemIdByServletPath(path));
                String currentApFunctionName = StringUtils.defaultString(EnvFacadeHelper.getItemNameByServletPath(path));

                // 如果需紀錄 Log :
                // 應用系統功能代號 = 空白, 代表 Log 功能的 undefineUrlLogging 有開啟, 則無論如何都得紀錄一筆 Portal Log
                // 如果 此次的應用系統功能代號 和 前次的應用系統功能代號 不同, 則表示使用者切換功能, 須紀錄一筆 Portal Log
                if (StringUtils.equals(currentApFunctionCode, "") || (!StringUtils.equals(previousApFunctionCode, currentApFunctionCode))) {
                    BigDecimal sysId = loggingService.loggingPortalLog(userData, request);

                    // 更新使用者物件之 Log 相關屬性
                    if (sysId != null) {
                        userData.setSysId(sysId); // 目前使用者作業之 Protal Log Id
                        userData.setApFunctionCode(currentApFunctionCode); // 使用者目前執行的功能的 應用系統功能代號
                        userData.setApFunctionName(currentApFunctionName); // 使用者目前執行的功能的 應用系統功能名稱
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("Portal Log 寫入錯誤, 原因:" + ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 產製 Query Log (<code>MMQUERYLOG</code>) 的 XML 內容, 範例如下: <code><pre>
     *  &lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;
     *  &lt;QueryLog&gt;
     *      &lt;TableName&gt;MEXXXTABLE&lt;/TableName&gt;
     *      &lt;QueryMethod method=&quot;selectPersonBy&quot;&gt;
     *          &lt;Condition&gt;
     *              &lt;ColumnName&gt;IDN&lt;/ColumnName&gt;
     *              &lt;Value&gt;A123456789&lt;/Value&gt;
     *          &lt;/Condition&gt;
     *          &lt;Condition&gt;
     *              &lt;ColumnName&gt;BRDTE&lt;/ColumnName&gt;
     *              &lt;Value&gt;0630101&lt;/Value&gt;
     *          &lt;/Condition&gt;
     *      &lt;/QueryMethod&gt;
     *  &lt;/QueryLog&gt;
     * </pre></code>
     * 
     * @return 成功產製回傳 XML 字串; 失敗回傳 null
     */
    public static String generateQueryLogXML(String tableName, String methodName, String[] fieldList, Object[] args) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(methodName) || fieldList == null || args == null)
            return null;

        StringBuffer xmlData = new StringBuffer();

        xmlData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        xmlData.append("<QueryLog>");
        xmlData.append("<TableName>" + tableName + "</TableName>");
        xmlData.append("<QueryMethod method=\"" + methodName + "\">");

        for (int i = 0; i < fieldList.length; i++) {
            xmlData.append("<Condition>");

            xmlData.append("<ColumnName>" + fieldList[i] + "</ColumnName>");

            String fieldValue = "";
            Converter converter = ConvertUtils.lookup(String.class);
            if (converter != null) {
                fieldValue = (String) converter.convert(String.class, args[i]);
            }
            xmlData.append("<Value>" + StrUtil.escapeXml(fieldValue) + "</Value>");

            xmlData.append("</Condition>");
        }

        xmlData.append("</QueryMethod>");
        xmlData.append("</QueryLog>");

        return xmlData.toString();
    }

    /**
     * 紀錄 AP Log for Insert
     * 
     * @param insertObj 新增後的 Domain Object
     */
    public static void doInsertLog(Object insertObj) {
        if (insertObj != null)
            doAplog(null, insertObj);
    }

    /**
     * 紀錄 AP Log for Insert (List 中的每個物件都屬於新增時使用)
     * 
     * @param insertList 新增後的 Domain Object List
     */
    @SuppressWarnings("unchecked")
    public static void doInsertListLog(List insertList) {
        if (insertList != null)
            doListAplog(null, insertList);
    }

    /**
     * 紀錄 AP Log for Update
     * 
     * @param beforeObj 改前 Domain Object
     * @param afterObj 改後 Domain Object
     */
    public static void doUpdateLog(Object beforeObj, Object afterObj) {
        if (beforeObj != null && afterObj != null)
            doAplog(beforeObj, afterObj);
    }

    /**
     * 紀錄 AP Log for Update<br>
     * 此方法會比對傳入的兩個 List, 並自動判斷改後 List 中的物之增刪修的情況
     * 
     * @param beforeList 改前 Domain Object List
     * @param afterList 改後 Domain Object List
     */
    @SuppressWarnings("unchecked")
    public static void doUpdateListLog(List beforeList, List afterList) {
        if (beforeList != null && afterList != null)
            doListAplog(beforeList, afterList);
    }

    /**
     * 紀錄 AP Log for Delete
     * 
     * @param deleteObj 刪除前的 Domain Object
     */
    public static void doDeleteLog(Object deleteObj) {
        if (deleteObj != null)
            doAplog(deleteObj, null);
    }

    /**
     * 紀錄 AP Log for Delete (List 中的每個物件都被刪除時使用)
     * 
     * @param deleteList 刪除前的 Domain Object List
     */
    @SuppressWarnings("unchecked")
    public static void doDeleteListLog(List deleteList) {
        if (deleteList != null)
            doListAplog(deleteList, null);
    }

    /**
     * AP Log
     * 
     * @param beforeObj 修改前物件
     * @param afterObj 修改後物件
     */
    private static void doAplog(Object beforeObj, Object afterObj) {
        try {
            LoggingService loggingService = (LoggingService) SpringHelper.getBeanById(ConstantKey.LOGGING_SERVICE_ID);

            // 判斷是否需紀錄 Log
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) { // ... [

                UserInfo userData = UserSessionHelper.getUserData(HttpHelper.getHttpRequest());

                // 使用者沒登入
                if (userData == null)
                    return;

                String accessType = null;
                String tableName = null;

                StringBuffer pkFieldBuffer = new StringBuffer("");
                StringBuffer fieldBuffer = new StringBuffer("");
                StringBuffer befImgBuffer = new StringBuffer("");
                StringBuffer aftImgBuffer = new StringBuffer("");
                String pkField = "";
                String field = "";
                String befImg = "";
                String aftImg = "";

                Object opObj = null;

                // 判斷 MMACCESSLG 操作類別 及 MMAPLOG 異動代號
                if (beforeObj == null && afterObj != null) {
                    accessType = ConstantKey.ACCESS_TYPE_INSERT;
                    opObj = afterObj;
                }
                else if (beforeObj != null && afterObj != null) {
                    accessType = ConstantKey.ACCESS_TYPE_UPDATE;
                    opObj = beforeObj;

                    // 修改前物件 和 修改後物件 不為同一 Class 則不做 AP Log
                    if (!StringUtils.equals(beforeObj.getClass().getName(), afterObj.getClass().getName())) {
                        log.error("AP Log 寫入錯誤, 原因: 修改前物件 和 修改後物件 並不是相同的 Class");
                        return;
                    }
                }
                else if (beforeObj != null && afterObj == null) {
                    accessType = ConstantKey.ACCESS_TYPE_DELETE;
                    opObj = beforeObj;
                }

                if (StringUtils.isNotBlank(accessType) && opObj != null) { // ... [
                    // 取得 Table Name
                    if (opObj.getClass().isAnnotationPresent(Table.class)) {
                        tableName = opObj.getClass().getAnnotation(Table.class).value();
                        // 如果 Table Name 是空的, 則以 Class 名稱轉大寫當 Table Name
                        if (StringUtils.isBlank(tableName))
                            tableName = StringUtils.upperCase(opObj.getClass().getSimpleName());
                    }

                    // 有定義 Table Name 再繼續取得 PKey List 及 Field List
                    if (StringUtils.isNotBlank(tableName)) { // ... [
                        Field[] fieldArray = opObj.getClass().getDeclaredFields();

                        for (Field fd : fieldArray) { // ... [
                            PropertyDescriptor prop = PropertyUtils.getPropertyDescriptor(opObj, fd.getName());
                            if (prop != null && prop.getReadMethod() != null) {
                                if (fd.isAnnotationPresent(PkeyField.class)) { // ... [
                                    String pkey = fd.getAnnotation(PkeyField.class).value();
                                    // 如果 PKey 名稱是空的, 則以 Property 名稱轉大寫當 PKey 名稱
                                    if (StringUtils.isBlank(pkey))
                                        pkey = StringUtils.upperCase(fd.getName());

                                    // 取得 PKey 的值
                                    Object pkeyValue = PropertyUtils.getSimpleProperty(opObj, fd.getName());
                                    pkFieldBuffer.append("," + pkey + "=" + StringUtils.defaultString(ConvertUtils.convert(pkeyValue)));
                                } // ] ... end if (fd.isAnnotationPresent(PkeyField.class))

                                if (fd.isAnnotationPresent(LogField.class)) { // ... [
                                    String logField = fd.getAnnotation(LogField.class).value();
                                    // 如果 Log Field 名稱是空的, 則以 Property 名稱轉大寫當 Log Field 名稱
                                    if (StringUtils.isBlank(logField))
                                        logField = StringUtils.upperCase(fd.getName());

                                    if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_INSERT)) {
                                        fieldBuffer.append("," + logField);
                                        // 取得 Log Field 的值
                                        Object aftValue = PropertyUtils.getSimpleProperty(opObj, fd.getName());
                                        aftImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(aftValue)));
                                    }
                                    else if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_UPDATE)) {
                                        Object befValue = PropertyUtils.getSimpleProperty(beforeObj, fd.getName());
                                        Object aftValue = PropertyUtils.getSimpleProperty(afterObj, fd.getName());
                                        // 值不相同才紀錄
                                        if (!StringUtils.equals(StringUtils.defaultString(ConvertUtils.convert(befValue)), StringUtils.defaultString(ConvertUtils.convert(aftValue)))) {
                                            fieldBuffer.append("," + logField);
                                            befImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(befValue)));
                                            aftImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(aftValue)));
                                        }
                                    }
                                    else if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_DELETE)) {
                                        fieldBuffer.append("," + logField);
                                        // 取得 Log Field 的值
                                        Object befValue = PropertyUtils.getSimpleProperty(opObj, fd.getName());
                                        befImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(befValue)));
                                    }
                                }
                            } // ] ... end if (fd.isAnnotationPresent(LogField.class))
                        } // ] ... end for (Field fd : fieldArray)

                        pkField = pkFieldBuffer.toString();
                        field = fieldBuffer.toString();
                        befImg = befImgBuffer.toString();
                        aftImg = aftImgBuffer.toString();

                        // 去除開頭的逗號
                        if (StringUtils.isNotBlank(pkField))
                            pkField = pkField.substring(1, pkField.length());
                        if (StringUtils.isNotBlank(field))
                            field = field.substring(1, field.length());
                        if (StringUtils.isNotBlank(befImg))
                            befImg = befImg.substring(1, befImg.length());
                        if (StringUtils.isNotBlank(aftImg))
                            aftImg = aftImg.substring(1, aftImg.length());

                        // 紀錄 AP LOG
                        if (StringUtils.isNotBlank(pkField) && StringUtils.isNotBlank(field)) {
                            loggingService.loggingApLog(userData, accessType, tableName, pkField, field, befImg, aftImg);
                        }
                    } // ] ... end if (StringUtils.isNotBlank(tableName))
                } // ] ... end if (StringUtils.isNotBlank(accessType) && opObj != null)
            } // ] ... end if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()))
        }
        catch (Exception e) {
            log.error("AP Log 寫入錯誤, 原因: " + ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * AP Log
     * 
     * @param beforeList 修改前物件 List
     * @param afterList 修改後物件 List
     */
    private static void doListAplog(List<Object> beforeList, List<Object> afterList) {
        try {
            LoggingService loggingService = (LoggingService) SpringHelper.getBeanById(ConstantKey.LOGGING_SERVICE_ID);

            // 判斷是否需紀錄 Log
            if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest())) { // ... [

                UserInfo userData = UserSessionHelper.getUserData(HttpHelper.getHttpRequest());

                // 使用者沒登入
                if (userData == null)
                    return;

                String accessType = null;
                String tableName = null;

                Object opObj = null;

                List<Mmaplog> apLogList = new ArrayList<Mmaplog>();

                // 判斷 MMACCESSLG 操作類別
                if (beforeList == null && afterList != null && afterList.size() > 0) {
                    accessType = ConstantKey.ACCESS_TYPE_INSERT;
                    opObj = afterList.get(0);
                }
                else if (beforeList != null && afterList != null) {
                    accessType = ConstantKey.ACCESS_TYPE_UPDATE;

                    String className = "";

                    if (beforeList.size() > 0) {
                        className = beforeList.get(0).getClass().getName();
                        opObj = beforeList.get(0);
                    }
                    else if (afterList.size() > 0) {
                        className = afterList.get(0).getClass().getName();
                        opObj = afterList.get(0);
                    }

                    if (StringUtils.isBlank(className)) {
                        log.error("AP Log 寫入錯誤, 原因: 修改前 List 和 修改後 List 大小皆為 0");
                        return;
                    }

                    // 修改前 List 和 修改後 List 不為同一 Class 則不做 AP Log
                    for (Object object : beforeList) {
                        if (!StringUtils.equals(object.getClass().getName(), className)) {
                            log.error("AP Log 寫入錯誤, 原因: 修改前 List 中的 Domain Object 不為相同之 Class");
                            return;
                        }
                    }

                    for (Object object : afterList) {
                        if (!StringUtils.equals(object.getClass().getName(), className)) {
                            log.error("AP Log 寫入錯誤, 原因: 修改後 List 中的 Domain Object 不為相同之 Class");
                            return;
                        }
                    }
                }
                else if (beforeList != null && afterList == null && beforeList.size() > 0) {
                    accessType = ConstantKey.ACCESS_TYPE_DELETE;
                    opObj = beforeList.get(0);
                }

                if (StringUtils.isNotBlank(accessType) && opObj != null) { // ... [
                    // 取得 Table Name
                    if (opObj.getClass().isAnnotationPresent(Table.class)) {
                        tableName = opObj.getClass().getAnnotation(Table.class).value();
                        // 如果 Table Name 是空的, 則以 Class 名稱轉大寫當 Table Name
                        if (StringUtils.isBlank(tableName))
                            tableName = StringUtils.upperCase(opObj.getClass().getSimpleName());
                    }

                    // 用來存放 Domain 中定義的 PkeyField 及 LogField 的 Property 名稱 (供後續加速處理速度用)
                    // Map Key - PropertyName ; Map Value - 代表的 DB 欄位名稱
                    Map<String, String> pkeyFieldMap = new HashMap<String, String>();
                    Map<String, String> logFieldMap = new HashMap<String, String>();

                    if (StringUtils.isNotBlank(tableName)) { // ... [
                        Field[] fieldArray = opObj.getClass().getDeclaredFields();

                        // 建立 PkeyField 及 LogField Map (供後續加速處理速度用)
                        // [
                        for (Field fd : fieldArray) { // ... [
                            PropertyDescriptor prop = PropertyUtils.getPropertyDescriptor(opObj, fd.getName());

                            if (PropertyUtils.isReadable(opObj, fd.getName()) && prop.getReadMethod() != null) {
                                if (fd.isAnnotationPresent(PkeyField.class)) { // ... [
                                    String pkey = fd.getAnnotation(PkeyField.class).value();
                                    // 如果 PKey 名稱是空的, 則以 Property 名稱轉大寫當 PKey 名稱
                                    if (StringUtils.isBlank(pkey))
                                        pkey = StringUtils.upperCase(fd.getName());

                                    pkeyFieldMap.put(fd.getName(), pkey);
                                } // ] ... end if (fd.isAnnotationPresent(PkeyField.class))

                                if (fd.isAnnotationPresent(LogField.class)) { // ... [
                                    String logField = fd.getAnnotation(LogField.class).value();
                                    // 如果 Log Field 名稱是空的, 則以 Property 名稱轉大寫當 Log Field 名稱
                                    if (StringUtils.isBlank(logField))
                                        logField = StringUtils.upperCase(fd.getName());

                                    logFieldMap.put(fd.getName(), logField);
                                } // ] ... end if (fd.isAnnotationPresent(LogField.class))
                            } // ] ... end if (prop.getReadMethod() != null)
                        } // ] ... end for (Field df : fieldArray)
                        // ]

                        if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_INSERT)) { // ... [
                            for (Object afterObj : afterList) {

                                StringBuffer pkFieldBuffer = new StringBuffer("");
                                StringBuffer fieldBuffer = new StringBuffer("");
                                StringBuffer aftImgBuffer = new StringBuffer("");
                                String pkField = "";
                                String field = "";
                                String aftImg = "";

                                for (String key : pkeyFieldMap.keySet()) {
                                    // 取得 PKey 的值
                                    Object pkeyValue = PropertyUtils.getSimpleProperty(afterObj, key);
                                    pkFieldBuffer.append("," + pkeyFieldMap.get(key) + "=" + StringUtils.defaultString(ConvertUtils.convert(pkeyValue)));
                                }

                                for (String key : logFieldMap.keySet()) {
                                    fieldBuffer.append("," + logFieldMap.get(key));
                                    // 取得 Log Field 的值
                                    Object aftValue = PropertyUtils.getSimpleProperty(afterObj, key);
                                    aftImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(aftValue)));
                                }

                                pkField = pkFieldBuffer.toString();
                                field = fieldBuffer.toString();
                                aftImg = aftImgBuffer.toString();

                                // 去除開頭的逗號
                                if (StringUtils.isNotBlank(pkField))
                                    pkField = pkField.substring(1, pkField.length());
                                if (StringUtils.isNotBlank(field))
                                    field = field.substring(1, field.length());
                                if (StringUtils.isNotBlank(aftImg))
                                    aftImg = aftImg.substring(1, aftImg.length());

                                // 產生 AP LOG 物件
                                if (StringUtils.isNotBlank(pkField) && StringUtils.isNotBlank(field)) {
                                    Mmaplog apLog = getApLogObject(userData, tableName, pkField, ConstantKey.ACCESS_TYPE_INSERT, field, null, aftImg);
                                    apLogList.add(apLog);
                                }
                            }

                            // 紀錄 AP LOG
                            if (apLogList.size() > 0) {
                                loggingService.loggingApLog(userData, accessType, tableName, apLogList);
                            }
                        } // ] ... end if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_INSERT))
                        else if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_UPDATE)) { // ... [

                            Map<String, Object> beforePkeyMap = new HashMap<String, Object>(); // beforeList 的 PKey Map
                            Map<String, Object> afterPkeyMap = new HashMap<String, Object>(); // afterList 的 PKey Map

                            // 把 List 轉為以 PkeyField 為 Key 值, Domain Object 為 Value 的 Map
                            // 後續比對利用 beforePkeyMap 及 afterPkeyMap 進行比對,
                            // 可快速找出哪些 Domain 是新增, 哪些是修改, 哪些是刪除
                            // [
                            for (Object beforeObj : beforeList) {
                                StringBuffer pkFieldBuffer = new StringBuffer("");
                                String pkField = "";

                                for (String key : pkeyFieldMap.keySet()) {
                                    // 取得 PKey 的值
                                    Object pkeyValue = PropertyUtils.getSimpleProperty(beforeObj, key);
                                    pkFieldBuffer.append("," + pkeyFieldMap.get(key) + "=" + StringUtils.defaultString(ConvertUtils.convert(pkeyValue)));
                                }

                                pkField = pkFieldBuffer.toString();

                                // 去除開頭的逗號
                                if (StringUtils.isNotBlank(pkField)) {
                                    pkField = pkField.substring(1, pkField.length());
                                    beforePkeyMap.put(pkField, beforeObj);
                                }
                            }

                            for (Object afterObj : afterList) {
                                StringBuffer pkFieldBuffer = new StringBuffer("");
                                String pkField = "";

                                for (String key : pkeyFieldMap.keySet()) {
                                    // 取得 PKey 的值
                                    Object pkeyValue = PropertyUtils.getSimpleProperty(afterObj, key);
                                    pkFieldBuffer.append("," + pkeyFieldMap.get(key) + "=" + StringUtils.defaultString(ConvertUtils.convert(pkeyValue)));
                                }

                                pkField = pkFieldBuffer.toString();

                                // 去除開頭的逗號
                                if (StringUtils.isNotBlank(pkField)) {
                                    pkField = pkField.substring(1, pkField.length());
                                    afterPkeyMap.put(pkField, afterObj);
                                }
                            }
                            // ]

                            for (String beforeKey : beforePkeyMap.keySet()) { // ... [
                                if (!afterPkeyMap.containsKey(beforeKey)) { // 表示這筆資料為刪除的資料

                                    Object objBef = beforePkeyMap.get(beforeKey);

                                    StringBuffer fieldBuffer = new StringBuffer("");
                                    StringBuffer befImgBuffer = new StringBuffer("");
                                    String field = "";
                                    String befImg = "";

                                    for (String key : logFieldMap.keySet()) {
                                        fieldBuffer.append("," + logFieldMap.get(key));
                                        // 取得 Log Field 的值
                                        Object befValue = PropertyUtils.getSimpleProperty(objBef, key);
                                        befImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(befValue)));
                                    }

                                    field = fieldBuffer.toString();
                                    befImg = befImgBuffer.toString();

                                    if (StringUtils.isNotBlank(field))
                                        field = field.substring(1, field.length());
                                    if (StringUtils.isNotBlank(befImg))
                                        befImg = befImg.substring(1, befImg.length());

                                    // 產生 AP LOG 物件
                                    if (StringUtils.isNotBlank(beforeKey) && StringUtils.isNotBlank(field)) {
                                        Mmaplog apLog = getApLogObject(userData, tableName, beforeKey, ConstantKey.ACCESS_TYPE_DELETE, field, befImg, null);
                                        apLogList.add(apLog);
                                    }
                                }
                                else { // 表示這筆資料為修改的資料

                                    // 取得改前改後的物件
                                    Object objBef = beforePkeyMap.get(beforeKey);
                                    Object objAft = afterPkeyMap.get(beforeKey);

                                    StringBuffer fieldBuffer = new StringBuffer("");
                                    StringBuffer befImgBuffer = new StringBuffer("");
                                    StringBuffer aftImgBuffer = new StringBuffer("");
                                    String field = "";
                                    String befImg = "";
                                    String aftImg = "";

                                    for (String key : logFieldMap.keySet()) {
                                        // 取得改前改後值
                                        Object befValue = PropertyUtils.getSimpleProperty(objBef, key);
                                        Object aftValue = PropertyUtils.getSimpleProperty(objAft, key);

                                        // 改前改後值不相同才紀錄
                                        if (!StringUtils.equals(StringUtils.defaultString(ConvertUtils.convert(befValue)), StringUtils.defaultString(ConvertUtils.convert(aftValue)))) {
                                            fieldBuffer.append("," + logFieldMap.get(key));
                                            // 取得 Log Field 的值
                                            befImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(befValue)));
                                            aftImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(aftValue)));
                                        }
                                    }

                                    field = fieldBuffer.toString();
                                    befImg = befImgBuffer.toString();
                                    aftImg = aftImgBuffer.toString();

                                    // 去除開頭的逗號
                                    if (StringUtils.isNotBlank(field))
                                        field = field.substring(1, field.length());
                                    if (StringUtils.isNotBlank(befImg))
                                        befImg = befImg.substring(1, befImg.length());
                                    if (StringUtils.isNotBlank(aftImg))
                                        aftImg = aftImg.substring(1, aftImg.length());

                                    // 產生 AP LOG 物件
                                    if (StringUtils.isNotBlank(beforeKey) && StringUtils.isNotBlank(field)) {
                                        Mmaplog apLog = getApLogObject(userData, tableName, beforeKey, ConstantKey.ACCESS_TYPE_UPDATE, field, befImg, aftImg);
                                        apLogList.add(apLog);
                                    }
                                }
                            } // ] ... end for (String beforeKey : beforePkeyMap.keySet())

                            for (String afterKey : afterPkeyMap.keySet()) { // ... [

                                if (!beforePkeyMap.containsKey(afterKey)) { // // 表示這筆資料為新增的資料

                                    Object objAft = afterPkeyMap.get(afterKey);

                                    StringBuffer fieldBuffer = new StringBuffer("");
                                    StringBuffer aftImgBuffer = new StringBuffer("");
                                    String field = "";
                                    String aftImg = "";

                                    for (String key : logFieldMap.keySet()) {
                                        fieldBuffer.append("," + logFieldMap.get(key));
                                        // 取得 Log Field 的值
                                        Object aftValue = PropertyUtils.getSimpleProperty(objAft, key);
                                        aftImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(aftValue)));
                                    }

                                    field = fieldBuffer.toString();
                                    aftImg = aftImgBuffer.toString();

                                    if (StringUtils.isNotBlank(field))
                                        field = field.substring(1, field.length());
                                    if (StringUtils.isNotBlank(aftImg))
                                        aftImg = aftImg.substring(1, aftImg.length());

                                    // 產生 AP LOG 物件
                                    if (StringUtils.isNotBlank(afterKey) && StringUtils.isNotBlank(field)) {
                                        Mmaplog apLog = getApLogObject(userData, tableName, afterKey, ConstantKey.ACCESS_TYPE_INSERT, field, null, aftImg);
                                        apLogList.add(apLog);
                                    }
                                }
                            } // ] ... end for (String afterKey : afterPkeyMap.keySet())

                            // 紀錄 AP LOG
                            if (apLogList.size() > 0) {
                                loggingService.loggingApLog(userData, accessType, tableName, apLogList);
                            }
                        } // ] ... end if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_UPDATE))
                        else if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_DELETE)) { // ... [
                            for (Object beforeObj : beforeList) {

                                StringBuffer pkFieldBuffer = new StringBuffer("");
                                StringBuffer fieldBuffer = new StringBuffer("");
                                StringBuffer befImgBuffer = new StringBuffer("");
                                String pkField = "";
                                String field = "";
                                String befImg = "";

                                for (String key : pkeyFieldMap.keySet()) {
                                    // 取得 PKey 的值
                                    Object pkeyValue = PropertyUtils.getSimpleProperty(beforeObj, key);
                                    pkFieldBuffer.append("," + pkeyFieldMap.get(key) + "=" + StringUtils.defaultString(ConvertUtils.convert(pkeyValue)));
                                }

                                for (String key : logFieldMap.keySet()) {
                                    fieldBuffer.append("," + logFieldMap.get(key));
                                    // 取得 Log Field 的值
                                    Object befValue = PropertyUtils.getSimpleProperty(beforeObj, key);
                                    befImgBuffer.append("," + StringUtils.defaultString(ConvertUtils.convert(befValue)));
                                }

                                pkField = pkFieldBuffer.toString();
                                field = fieldBuffer.toString();
                                befImg = befImgBuffer.toString();

                                // 去除開頭的逗號
                                if (StringUtils.isNotBlank(pkField))
                                    pkField = pkField.substring(1, pkField.length());
                                if (StringUtils.isNotBlank(field))
                                    field = field.substring(1, field.length());
                                if (StringUtils.isNotBlank(befImg))
                                    befImg = befImg.substring(1, befImg.length());

                                // 產生 AP LOG 物件
                                if (StringUtils.isNotBlank(pkField) && StringUtils.isNotBlank(field)) {
                                    Mmaplog apLog = getApLogObject(userData, tableName, pkField, ConstantKey.ACCESS_TYPE_DELETE, field, befImg, null);
                                    apLogList.add(apLog);
                                }
                            }

                            // 紀錄 AP LOG
                            if (apLogList.size() > 0) {
                                loggingService.loggingApLog(userData, accessType, tableName, apLogList);
                            }
                        } // ] ... end if (StringUtils.equals(accessType, ConstantKey.ACCESS_TYPE_DELETE))

                    } // ] ... end if (StringUtils.isNotBlank(tableName))
                } // ] ... end if (StringUtils.isNotBlank(accessType) && opObj != null)
            } // ] ... end if (EnvFacadeHelper.isNeedLogging(HttpHelper.getHttpRequest()))
        }
        catch (Exception e) {
            log.error("AP Log 寫入錯誤, 原因: " + ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 建立 <code>Mmaplog</code> 物件
     * 
     * @param userData 使用者物件
     * @param tableName 異動的 TABLE 名稱
     * @param pkField 異動 TABLE 主鍵
     * @param accessType 異動代號
     * @param field 異動欄位
     * @param befImg 改前內容
     * @param aftImg 改後內容
     * @return <code>Mmaplog</code> 物件
     */
    public static Mmaplog getApLogObject(UserInfo userData, String tableName, String pkField, String accessType, String field, String befImg, String aftImg) {
        Mmaplog apLog = new Mmaplog();

        apLog.setTableName(tableName); // 異動 TABLE 名稱
        apLog.setPkField(pkField); // 異動 TABLE 主鍵
        // apLog.setChgTime(acsTime); // 異動時間 - Service 處理
        apLog.setPgmName(userData.getApFunctionName()); // 程式名稱
        apLog.setPgmCode(userData.getApFunctionCode()); // 程式代碼
        apLog.setDeptId(userData.getDeptId()); // 異動員工部門代號
        apLog.setModifyMan(userData.getUserId()); // 異動員工編號
        apLog.setTermEd(userData.getLoginIP()); // 終端機位址
        apLog.setChgCode(accessType); // 異動代號
        apLog.setField(field); // 異動欄位
        apLog.setBefImg(StringUtils.defaultString(befImg)); // 改前內容
        apLog.setAftImg(StringUtils.defaultString(aftImg)); // 改後內容
        apLog.setIdNo(""); // 證號
        apLog.setMemo(""); // 備註
        // apLog.setSno(sno); // 編號 - Service 處理

        return apLog;
    }
}
