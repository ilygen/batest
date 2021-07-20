package tw.gov.bli.common;

public class ConstantKey {
    /**
     * 使用者物件在 Session 中的 Key
     */
    public static final String USER_INFO = "_scope_userInfo";

    /**
     * 應用系統代號 於 web.xml 中 <code>&lt;context-param&gt;</code> 定義的 <code>&lt;param-name&gt;</code>
     */
    public static final String SYSTEM_ID = "systemId";

    /**
     * 應用系統名稱 於 web.xml 中 <code>&lt;context-param&gt;</code> 定義的 <code>&lt;param-name&gt;</code>
     */
    public static final String SYSTEM_NAME = "systemName";
    
    /**
     * 是否啟用 CAS 於 web.xml 中 <code>&lt;context-param&gt;</code> 定義的 <code>&lt;param-name&gt;</code>
     */
    public static final String ENABLE_CAS = "enableCAS";

    /**
     * <code>tw.gov.bli.common.services.SysfuncService</code> 於 Spring Config 中定義的 Bean ID
     */
    public static final String SYSTEM_FUNCTION_SERVICE_ID = "sysfuncService";

    /**
     * <code>tw.gov.bli.common.services.LoggingService</code> 於 Spring Config 中定義的 Bean ID
     */
    public static final String LOGGING_SERVICE_ID = "loggingService";

    /**
     * MMACCESSLG.ACCTYPE = 'I'
     */
    public static final String ACCESS_TYPE_INSERT = "I";

    /**
     * MMACCESSLG.ACCTYPE = 'U'
     */
    public static final String ACCESS_TYPE_UPDATE = "U";

    /**
     * MMACCESSLG.ACCTYPE = 'D'
     */
    public static final String ACCESS_TYPE_DELETE = "D";

    /**
     * MMACCESSLG.ACCTYPE = 'Q'
     */
    public static final String ACCESS_TYPE_QUERY = "Q";

    /**
     * MMACCESSLG.ACCTYPE = 'P'
     */
    public static final String ACCESS_TYPE_PRINT = "P";

}
