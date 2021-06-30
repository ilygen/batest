package tw.gov.bli.ba.util;

import java.io.StringReader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;

public class StringUtility {
    private static final int PAD_LIMIT = 8192;

    /**
     * 將字串右邊補上指定的字串到某長度<br>
     * 可處理中文, 會將中文字的長度視為 2<br>
     * 例如:<br>
     * 
     * <pre>
     * StringUtility.chtRightPad(&quot;abc&quot;, 6, &quot;1&quot;) 將回傳 &quot;abc111&quot;
     * StringUtility.chtRightPad(&quot;張三&quot;, 6, &quot;1&quot;) 將回傳 &quot;張三11&quot;
     * StringUtility.chtRightPad(&quot;張三&quot;, 6, &quot;abc&quot;) 將回傳 &quot;張三ab&quot;
     * StringUtility.chtRightPad(&quot;abc&quot;, 2, &quot;1&quot;) 將回傳 &quot;abc&quot;
     * StringUtility.chtRightPad(null, 3, &quot;1&quot;) 將回傳 &quot;111&quot;
     * </pre>
     * 
     * @param str 字串
     * @param length 欲補足的總長度
     * @param padStr 欲補的字元 (不可是全形, 若欲補全形請用 jakarta commons.lang)
     * @return
     */
    public static String chtRightPad(String str, int length, String padStr) {
        str = StringUtils.defaultString(str);

        int padLen = stringRealLength(padStr);
        int strLen = stringRealLength(str);

        int pads = length - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            for (int i = 0; i < (length - strLen); i++) {
                str = str + padStr;
            }
            return str;
        }
        if (pads == padLen) {
            return str.concat(padStr);
        }
        else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }

    /**
     * 將字串左邊補上指定的字串到某長度<br>
     * 可處理中文, 會將中文字的長度視為 2<br>
     * 例如:<br>
     * 
     * <pre>
     * StringUtility.chtLeftPad(&quot;abc&quot;, 6, &quot;1&quot;) 將回傳 &quot;111abc&quot;
     * StringUtility.chtLeftPad(&quot;張三&quot;, 6, &quot;1&quot;) 將回傳 &quot;11張三&quot;
     * StringUtility.chtLeftPad(&quot;張三&quot;, 6, &quot;abc&quot;) 將回傳 &quot;ab張三&quot;
     * StringUtility.chtLeftPad(&quot;abc&quot;, 2, &quot;1&quot;) 將回傳 &quot;abc&quot;
     * StringUtility.chtLeftPad(null, 3, &quot;1&quot;) 將回傳 &quot;111&quot;
     * </pre>
     * 
     * @param str 字串
     * @param length 欲補足的總長度
     * @param padStr 欲補的字串 (不可是全形, 若欲補全形請用 jakarta commons.lang)
     * @return
     */
    public static String chtLeftPad(String str, int length, String padStr) {
        str = StringUtils.defaultString(str);

        int padLen = stringRealLength(padStr);
        int strLen = stringRealLength(str);

        int pads = length - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            for (int i = 0; i < (length - strLen); i++) {
                str = padStr + str;
            }
            return str;
        }

        if (pads == padLen) {
            return padStr.concat(str);
        }
        else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

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

    /**
     * 將字串轉成指定編碼
     * 
     * @param str
     * @param encode
     * @return 編碼後的字串, 若轉碼失敗則回傳原字串
     */
    public static String convertString(String str, String encode) {
        StringReader reader = new StringReader(str);
        try {
            byte[] strBytes = IOUtils.toByteArray(reader, encode);
            return new String(strBytes);
        }
        catch (Exception e) {
            return str;
        }
        finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /**
     * 將字串中的空白及全形空白去除
     * 
     * @param str
     * @return
     */
    public static String replaceSpaceString(String str) {
        return StringUtils.replace(StringUtils.replace(StringUtils.trimToEmpty(str), " ", ""), "　", "");
    }

    /**
     * 將字串中的半形英文轉全形
     * 
     * @param str
     * @return
     */
    public static String encodeFromAscii(String str) {
        if (StringUtils.isBlank(str))
            return str;

        String asciiStr = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_`abcdefghijklmnopqrstuvwxyz{|}~";
        String encodeStr = "　！”＃＄％＆’（）＊＋，－．／０１２３４５６７８９：；＜＝＞？＠ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ〔＼〕＿‵ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ｛｜｝～";

        StringBuffer returnString = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            String sTemp = str.substring(i, i + 1);
            int nPos = StringUtils.indexOf(asciiStr, sTemp);

            if (nPos >= 0)
                sTemp = StringUtils.substring(encodeStr, nPos, nPos + 1);

            returnString.append(sTemp);
        }

        return returnString.toString();
    }

    /**
     * 將字串中的全形英文轉半形
     * 
     * @param str
     * @return
     */
    public static String decodeToAscii(String str) {
        if (StringUtils.isBlank(str))
            return str;

        String asciiStr = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_`abcdefghijklmnopqrstuvwxyz{|}~";
        String encodeStr = "　！”＃＄％＆’（）＊＋，－．／０１２３４５６７８９：；＜＝＞？＠ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ〔＼〕＿‵ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ｛｜｝～";

        StringBuffer returnString = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            String sTemp = str.substring(i, i + 1);
            int nPos = StringUtils.indexOf(encodeStr, sTemp);

            if (nPos >= 0)
                sTemp = StringUtils.substring(asciiStr, nPos, nPos + 1);

            returnString.append(sTemp);
        }

        return returnString.toString();
    }

    /**
     * 轉換 '<','<=','=','==','>=','>','<>','!=' 變成 'lt','le','eq','ge','gt','ne'
     * 
     * @param op
     * @return
     */
    public static String changOperator(String op) {
        op = StringUtils.trim(op);
        if (StringUtils.equals("<", op)) {
            return ConstantKey.OPERATOR_LESS_THAN;
        }
        else if (StringUtils.equals("<=", op)) {
            return ConstantKey.OPERATOR_LESS_EQUAL;
        }
        else if (StringUtils.equals("==", op) || StringUtils.equals("=", op)) {
            return ConstantKey.OPERATOR_EQUAL;
        }
        else if (StringUtils.equals(">=", op)) {
            return ConstantKey.OPERATOR_GREATER_EQUAL;
        }
        else if (StringUtils.equals(">", op)) {
            return ConstantKey.OPERATOR_GREATER_THAN;
        }
        else if (StringUtils.equals("<>", op) || StringUtils.equals("><", op) || StringUtils.equals("!=", op)) {
            return ConstantKey.OPERATOR_NOT_EQUAL;
        }
        else if (StringUtils.equalsIgnoreCase("in", op)) {
            return ConstantKey.OPERATOR_IN;
        }
        else if (StringUtils.equalsIgnoreCase("not in", op)) {
            return ConstantKey.OPERATOR_NOT_IN;
        }
        else if (StringUtils.equalsIgnoreCase("is null", op)) {
            return ConstantKey.OPERATOR_IS_NULL;
        }
        else if (StringUtils.equalsIgnoreCase("is not null", op)) {
            return ConstantKey.OPERATOR_IS_NOT_NULL;
        }
        else {
            return null;
        }
    }

    /**
     * 過濾字串，防止CRLF Injection
     * 
     * @param input
     * @return
     */
    public static final String removeControlCharacter(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.codePointCount(0, input.length()); i++) {
            int codePoint = input.codePointAt(i);
            if (!Character.isISOControl(codePoint)) {
                sb.appendCodePoint(codePoint);
            }
        }
        return sb.toString();
    }

    /**
     * 過濾字串，防止LDAP Injection
     * 
     * @param input
     * @return
     */
    public static String escapeDN(String name) {
        StringBuffer sb = new StringBuffer(); // If using JDK >= 1.5 consider using StringBuilder
        if ((name.length() > 0) && ((name.charAt(0) == ' ') || (name.charAt(0) == '#'))) {
            sb.append('\\'); // add the leading backslash if needed
        }
        for (int i = 0; i < name.length(); i++) {
            char curChar = name.charAt(i);
            switch (curChar) {
                case '\\':
                    sb.append("\\\\");
                    break;
                // case ',':
                // sb.append("\\,");
                // break;
                case '+':
                    sb.append("\\+");
                    break;
                case '"':
                    sb.append("\\\"");
                    break;
                case '<':
                    sb.append("\\<");
                    break;
                case '>':
                    sb.append("\\>");
                    break;
                case ';':
                    sb.append("\\;");
                    break;
                default:
                    sb.append(curChar);
            }
        }
        // if ((name.length() > 1) && (name.charAt(name.length() - 1) == ' ')) {
        // sb.insert(sb.length() - 1, '\\'); // add the trailing backslash if needed
        // }
        return sb.toString();
    }

}
