package tw.gov.bli.ba.util;

import org.apache.commons.lang.StringUtils;

/**
 * Class for Presentation Support
 * 
 * @author Goston
 */
public class PresentationUtility {

    // 隱藏 身分證號 後 X 碼
    private static final int IDN_SHADOW_LENGTH = 4;
    // 隱藏 身分證號 之替換字元
    private static final String IDN_SHADOW_CHAR = "*";

    /**
     * 隱藏 身分證號 後 X 碼 <br>
     * (X 由 PresentationUtility.IDN_SHADOW_LENGTH) 指定<br>
     * 
     * @param idN 身分證號
     * @return 處理過後的 身分證號
     */
    public static String shadowIdN(String idN) {
        int length = StringUtils.length(idN);
        if (length <= IDN_SHADOW_LENGTH) {
            return idN;
        }
        else {
            return StringUtils.rightPad(idN.substring(0, length - IDN_SHADOW_LENGTH), length, IDN_SHADOW_CHAR);
        }
    }

    /**
     * 將民國年月日轉成西元年月日 或 將西元年月日轉成民國年月日 <br>
     * 此方法僅供頁面顯示用, 轉換後之民國年月日如果為民前<br>
     * 則顯示 "民國前 xxxxxxx", ex: "民國前 0010114"<br>
     * 
     * <pre>
     * 用法:
     *   changeDateType(&quot;0950101&quot;) 傳回 20060101
     *   changeDateType(&quot;20060101&quot;) 傳回 0950101
     *   changeDateType(&quot;19100101&quot;) 傳回 民國前 0020101
     * </pre>
     * 
     * @param sDate java.lang.String 欲轉換的日期字串
     * @return java.lang.String
     */
    public static String changeDateTypeForDisplay(String sDate) {
        if (StringUtils.length(sDate) == 8) {
            if (StringUtils.equals(sDate.substring(0, 1), "-"))
                return DateUtility.changeDateType(sDate.substring(1, 8), true);
            else {
                if (Integer.parseInt(sDate.substring(0, 4)) < 1912)
                    return "民國前 " + DateUtility.changeDateType(sDate, true);
                else
                    return DateUtility.changeDateType(sDate, false);
            }
        }
        else if (StringUtils.length(sDate) == 7) {
            return DateUtility.changeDateType(sDate, false);
        }
        else {
            return "";
        }
    }
}
