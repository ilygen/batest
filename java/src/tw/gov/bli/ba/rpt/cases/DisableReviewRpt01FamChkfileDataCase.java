package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單 - 編審資料 - 眷屬編審註記
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01FamChkfileDataCase implements Serializable{
    
    
    private String payYm; // 給付年月
    private String chkCode; // 編審註記代碼
    private String chkCodePre; //編審註記代號類型
    private String chkResult; //編審註記說明
    
    
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
    
    public DisableReviewRpt01FamChkfileDataCase(){
        
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

    public String getChkCodePre() {
        return chkCodePre;
    }

    public void setChkCodePre(String chkCodePre) {
        this.chkCodePre = chkCodePre;
    }

    public String getChkResult() {
        return chkResult;
    }

    public void setChkResult(String chkResult) {
        this.chkResult = chkResult;
    }
    
    
}
