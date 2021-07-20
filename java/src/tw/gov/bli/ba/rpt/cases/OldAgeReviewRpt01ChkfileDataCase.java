package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保老年年金給付受理編審清單 - 編審資料 - 事故者編審註記
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01ChkfileDataCase implements Serializable {
    private String payYm; // 給付年月
    private String chkCode; // 編審註記代碼

    /**
     * 給付年月
     * @return
     */
    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYm), false);
        else
            return StringUtils.defaultString(payYm);
    }
    
    public OldAgeReviewRpt01ChkfileDataCase() {

    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getChkCode() {
        return chkCode;
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }

}
