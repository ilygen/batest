package tw.gov.bli.ba.helper;

import java.util.Properties;

/**
 * DB 參數檔
 * 
 * @author jerry
 */
public class PropertyHelper {

    private static Properties properties;

    /**
     * 取得DB參數檔的值
     * 
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void initailProperties(Properties properties) {
        if (PropertyHelper.properties == null)
            PropertyHelper.properties = properties;
    }

}
