package tw.gov.bli.common.util;

import java.io.StringReader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class StrUtil {
    /**
     * 計算字串長度 (中文長度為 2, 英文長度為 1)
     * 
     * @param str
     * @return
     */
    public static int stringRealLength(String str) {
        if (str == null)
            return 0;
        
        if (StringUtils.containsIgnoreCase(System.getProperties().getProperty("os.name"), "windows")) {
            // 中文 Windows 平台預設 I/O 編碼為 MS950, 因此呼叫 "中文".getBytes().length 傳回的長度為 4
            return str.getBytes().length;
        }
        else {
            // UNIX 平台為防止預設 I/O 編碼不為 Big5, 因此強制轉為 Big5 再計算字串長度
            StringReader reader = new StringReader(str);
            try {
                int nLength = IOUtils.toByteArray(reader, "MS950").length;
                return nLength;
            }
            catch (Exception e) {
                return StringUtils.length(str);
            }
            finally {
                IOUtils.closeQuietly(reader);
            }
        }
    }

    /**
     * 取得子字串 (中文長度為 2, 英文長度為 1)
     * 
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String chtStrSubstring(String str, int beginIndex, int endIndex) {
        if (StringUtils.isBlank(str))
            return null;

        StringReader reader = new StringReader(str);

        try {
            byte[] strBytes = IOUtils.toByteArray(reader, "MS950");
            int nLength = strBytes.length;
            if (endIndex > nLength)
                endIndex = nLength;

            return new String(ArrayUtils.subarray(strBytes, beginIndex, endIndex), "MS950");
        }
        catch (Exception e) {
            return null;
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /**
     * 將傳入的字串轉成 String Array, 並去掉 Array 中每個字串的頭尾空白
     * 
     * @param str 欲轉成 Array 的字串
     * @param separator 字串的分隔符號
     * @return String Array
     */
    public static String[] splitTrim(String str, String separator) {
        String[] returnStringArray = StringUtils.splitByWholeSeparator(str, separator);
        for (int i = 0; i < returnStringArray.length; i++) {
            returnStringArray[i] = returnStringArray[i].trim();
        }

        return returnStringArray;
    }
    
    /**
     * 處理 XML 跳脫字元<br>
     * 由於 StringEscapeUtils.escapeXml() 中文字會轉編碼成類似: <code>&amp;#32769;</code> 故另寫 function 處理
     * 
     * @param value
     * @return
     */
    public static String escapeXml(String value) {
        if (value == null)
            return "";

        return StringUtils.replaceEach(value, new String[] { "\"", "'", "&", "<", ">" }, new String[] { "&quot;", "&apos;", "&amp;", "&lt;", "&gt;" });
    }
}
