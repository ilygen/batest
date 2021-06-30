package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;

/**
 * Detail Case for 紓困貸款抵銷情形照會單
 * 
 * @author Goston
 */
public class DataUpdateRpt03DetailCase implements Serializable {
    // 現金給付參考檔 (PBBMSA)
    // [
    private String bmApNo; // 受理號碼
    // ]

    /**
     * 受理號碼 14 碼<br>
     * 格式: xxx-x-xx-xxxxxx-xx
     * 
     * @return
     */
    public String getBmApNoString() {
        if (StringUtils.length(bmApNo) == 14)
            return bmApNo.substring(0, 3) + "-" + bmApNo.substring(3, 4) + "-" + bmApNo.substring(4, 6) + "-" + bmApNo.substring(6, 12) + "-" + bmApNo.substring(12, 14);
        else
            return StringUtils.defaultString(bmApNo);
    }

    /**
     * 依受理編號 第5、6碼 取得 xx 給付科<br>
     * <br>
     * 31 = 傷殘給付科<br>
     * 41 = 生老給付科<br>
     * 51、52、61 = 死亡給付科<br>
     * 91、92、82、84、85 = 職災醫療給付科<br>
     * 71、73、75 = 失業給付科<br>
     * 13、14、33、53 = 受託處給付科<br>
     * 
     * @return
     */
    public String getBmApNoDeptString() {
        if (StringUtils.length(bmApNo) >= 6) {
            String code = bmApNo.substring(4, 6);

            if (StringUtils.equals(code, "31"))
                return "傷殘";
            else if (StringUtils.equals(code, "41"))
                return "生老";
            else if (StringUtils.equals(code, "51") || StringUtils.equals(code, "52") || StringUtils.equals(code, "61"))
                return "死亡";
            else if (StringUtils.equals(code, "91") || StringUtils.equals(code, "92") || StringUtils.equals(code, "82") || StringUtils.equals(code, "84") || StringUtils.equals(code, "85"))
                return "職災醫療";
            else if (StringUtils.equals(code, "71") || StringUtils.equals(code, "73") || StringUtils.equals(code, "75"))
                return "失業";
            else if (StringUtils.equals(code, "13") || StringUtils.equals(code, "14") || StringUtils.equals(code, "33") || StringUtils.equals(code, "53"))
                return "受託處";
            else
                return "";
        }
        else {
            return "";
        }
    }

    public DataUpdateRpt03DetailCase() {

    }

    public String getBmApNo() {
        return bmApNo;
    }

    public void setBmApNo(String bmApNo) {
        this.bmApNo = bmApNo;
    }
}
