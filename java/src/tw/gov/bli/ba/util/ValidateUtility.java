package tw.gov.bli.ba.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidateUtility {
    private static Log log = LogFactory.getLog(ValidateUtility.class);

    /**
     * 檢查營利事業統一編號
     * 
     * @param banNo
     * @return
     */
    public static boolean validateBanNo(String banNo) {
        banNo = StringUtils.defaultString(banNo);

        char[] acID = new char[8];
        boolean ret = false;
        int[] D = new int[8];
        int i, wk1, wk2, wk3, wk4, wk5, wk6, wk7, wk8, wk9, wk10, wk11, wk_total, wk_check;

        try {
            if (banNo.length() == 8) {
                acID = banNo.toCharArray();
                for (i = 0; i < 8; i++) {
                    D[i] = Integer.parseInt(String.valueOf(acID[i]));
                }
                wk1 = D[0] + D[2] + D[4] + D[7];
                wk2 = D[1] * 2 / 10;
                wk3 = D[3] * 2 / 10;
                wk4 = D[5] * 2 / 10;
                wk5 = D[6] * 4 / 10;
                wk6 = D[1] * 2 % 10;
                wk7 = D[3] * 2 % 10;
                wk8 = D[5] * 2 % 10;
                wk9 = D[6] * 4 % 10;
                wk10 = (wk5 + wk9) / 10;
                wk_total = wk1 + wk2 + wk3 + wk4 + wk5 + wk6 + wk7 + wk8 + wk9;
                wk_check = wk_total % 10;
                if (wk_check == 0) {
                    ret = true;
                }
                else {
                    if (D[6] == 6) {
                        wk_total = wk1 + wk2 + wk3 + wk4 + wk6 + wk7 + wk8 + wk10;
                        wk_check = wk_total % 10;
                        if (wk_check == 0) {
                            ret = true;
                        }
                    }
                }
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
        return ret;
    }

    /**
     * 身分證號有效字元檢查
     * 
     * @param idNo
     * @return
     */
    public static boolean isValidIdNoChar(String idNo) {
        if (StringUtils.defaultString(idNo).trim().length() != 10)
            return false;

        String IDN_STR = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        String NUM_STR = "0123456789";

        // 首碼不是英文字母
        if (StringUtils.indexOf(IDN_STR, idNo.charAt(0)) == -1) {
            return false;
        }

        // 第二碼不是 1 或 2
        if (!StringUtils.equals(idNo.substring(1, 2), "1") && !StringUtils.equals(idNo.substring(1, 2), "2")) {
            return false;
        }

        // 其餘各碼須為數字
        for (int i = 2; i < idNo.length(); i++) {
            if (StringUtils.indexOf(NUM_STR, idNo.charAt(i)) == -1) {
                return false;
            }
        }

        return true;
    }

    /**
     * 檢查身分證號碼
     * 
     * @param personId
     * @return
     */
    public static boolean validatePersonIdNo(String personId) {
        personId = StringUtils.defaultString(personId);

        if (!isValidIdNoChar(personId))
            return false;

        char[] acID = new char[10];
        boolean ret = false;
        int[] letter = { 10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33 };
        int[] D = new int[10];
        int i, sum;

        /* Perform the rocID method. */
        try {
            if (personId.length() == 10) {
                acID = personId.toUpperCase().toCharArray();
                for (i = 1; i < 10; i++) {
                    D[i] = Integer.parseInt(String.valueOf(acID[i]));
                }
                if ((acID[0] >= 'A') && (acID[0] <= 'Z')) {
                    i = letter[((int) acID[0] - 65)];
                    sum = i / 10 + (i % 10) * 9;
                    sum = sum + 8 * D[1] + 7 * D[2] + 6 * D[3] + 5 * D[4] + 4 * D[5] + 3 * D[6] + 2 * D[7] + D[8] + D[9];
                    if ((sum % 10) == 0) {
                        ret = true;
                    }
                } // if letter.containsKey(aaa.charAt(1))
            } // if (sID.length() == 10)
        }
        catch (NumberFormatException e) {
            return false;
        }

        return ret;
    }
}
