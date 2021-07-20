package tw.gov.bli.ba.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

public class BaBusinessUtility {
    private static Log log = LogFactory.getLog(BaBusinessUtility.class);

    /**
     * 計算年齡, 若日期格式錯誤則傳回 -1 <br>
     * 依傳入的出生日期以及比對日期計算年齡<br>
     * 勞保局規則:生日前一天即滿一歲<br>
     * ex: 0600602 生日, 則 0970601 滿27歲;<br>
     * 0600601 生日, 則 0970531 滿27歲;<br>
     * 可傳入 民國 / 民前 / 西元 年月日 <br>
     * 
     * <pre>
     * 用法:
     *   projectApplyAge(&quot;0970512&quot;, &quot;0700612&quot;) 傳回 26
     *   projectApplyAge(&quot;0970611&quot;, &quot;0700612&quot;) 傳回 27
     *   projectApplyAge(&quot;0970531&quot;, &quot;0700602&quot;) 傳回 26
     *   projectApplyAge(&quot;0970531&quot;, &quot;0700601&quot;) 傳回 27
     *   projectApplyAge(&quot;0971105&quot;, &quot;-0010531&quot;) 傳回 97
     * </pre>
     * 
     * @param compareDate 比對日期
     * @param brDate 出生日期
     * @return String
     */
    public static String calAge(String compareDate, String brDate) {
        String age = "-1";
        if (compareDate.length() == 7 && Integer.parseInt(compareDate) > 0) {
            int nYear = Integer.parseInt(compareDate.substring(0, 3)) + 1911;
            compareDate = Integer.toString(nYear) + compareDate.substring(3, 7);
        }
        if (compareDate.length() == 8 && Integer.parseInt(compareDate) < 0) {
            int nYear = Integer.parseInt(compareDate.substring(0, 4)) + 1912;
            compareDate = Integer.toString(nYear) + compareDate.substring(4, 8);
        }

        if (brDate.length() == 7 && Integer.parseInt(brDate) > 0) {
            int nYear = Integer.parseInt(brDate.substring(0, 3)) + 1911;
            brDate = Integer.toString(nYear) + brDate.substring(3, 7);
        }
        if (brDate.length() == 8 && Integer.parseInt(brDate) < 0) {
            int nYear = Integer.parseInt(brDate.substring(0, 4)) + 1912;
            brDate = Integer.toString(nYear) + brDate.substring(4, 8);
        }
        
        // 處理不正確的出生日期
        if (!DateUtility.isValidDate(brDate)) {
            brDate = StringUtils.substring(brDate, 0, 6) + String.valueOf(DateUtility.lastDayUseYm(brDate));
        }

        // 若生日為0229 且申請日期為非閏年, 則生日用0301來看
        if (("0229").equals(brDate.substring(4, 8)) && DateUtility.isLeapYear(compareDate)) {
            brDate = brDate.substring(0, 4) + "0301";
        }

        if (brDate.length() == 8 && compareDate.length() == 8) {
            // 生日前一天即滿一歲, 因此先將生日日期往前推一天
            // brDate = DateUtility.calDay(brDate, -1);

            if (Integer.parseInt(compareDate.substring(4, 8)) >= Integer.parseInt(brDate.substring(4, 8))) {
                age = String.valueOf(Integer.parseInt(compareDate.substring(0, 4)) - Integer.parseInt(brDate.substring(0, 4)));
            }
            else {
                age = String.valueOf(Integer.parseInt(compareDate.substring(0, 4)) - Integer.parseInt(brDate.substring(0, 4)) - 1);
            }
        }
        return age;
    }

    /**
     * 依傳入貸方科目 計算貸方科目 檢查碼
     * 
     * @param 給付別
     * @param issuYm 核定年月
     * @return
     */
    public static String getAccountChkMk(String account) {
        String chkMk = "";
        int countNum = 11;

        if (account.length() == ConstantKey.ACCOUNT_LENGTH) {
            int sumNum = 0;
            for (int i = 0; i < 14; i++) {
                if (i != 6 && i != 7 && i != 13) {
                    sumNum += Integer.valueOf(account.substring(i, i + 1)) * (i + 1);
                }
            }
            sumNum = countNum - (sumNum % countNum);
            if (sumNum == 10) {
                chkMk = "0";
            }
            else if (sumNum == 11) {
                chkMk = "A";
            }
            else {
                chkMk = String.valueOf(sumNum);
            }
        }
        return chkMk;
    }
}
