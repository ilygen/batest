package tw.gov.bli.ba.framework.helper;


/**
 * 用來存放及取得系統環境參數之 Helper
 * 
 * @author Goston
 */
public class EnvHelper {
    /**
     * Web Root 路徑
     */
    public static String webpath = "";

    /**
     * 細明體字型檔路徑
     */
    public static String sungPath = "";

    /**
     * 標楷體字型檔路徑
     */
    public static String kaiPath = "";
    
    /**
     * 浮水印等圖檔的路徑
     */
    public static String resourcePath = "";
    
    /**
     * Excel Template 路徑
     */
    public static String xlsTemplatePath = "";

    public static void setWebpath(String webpath) {
        EnvHelper.webpath = webpath;
    }

    public static void setSungPath(String sungPath) {
        EnvHelper.sungPath = sungPath;
    }

    public static void setKaiPath(String kaiPath) {
        EnvHelper.kaiPath = kaiPath;
    }

    public static void setResourcePath(String resourcePath) {
        EnvHelper.resourcePath = resourcePath;
    }
    
    public static void setXlsTemplatePath(String xlsTemplatePath) {
        EnvHelper.xlsTemplatePath = xlsTemplatePath;
    }
}
