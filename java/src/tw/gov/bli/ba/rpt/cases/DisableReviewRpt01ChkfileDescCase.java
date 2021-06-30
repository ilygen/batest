package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;

/**
 * Case for 勞保失能年金給付受理編審清單 - 編審資料 - 編審註記說明
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01ChkfileDescCase implements Serializable {
    
    private String chkCode; // 編審註記代碼
    private String chkCodePost; // 編審註記代號類型
    private String chkResult; // 編審註記名稱
    public String getChkCode() {
        return chkCode;
    }
    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }
    public String getChkCodePost() {
        return chkCodePost;
    }
    public void setChkCodePost(String chkCodePost) {
        this.chkCodePost = chkCodePost;
    }
    public String getChkResult() {
        return chkResult;
    }
    public void setChkResult(String chkResult) {
        this.chkResult = chkResult;
    }

    
    

}
