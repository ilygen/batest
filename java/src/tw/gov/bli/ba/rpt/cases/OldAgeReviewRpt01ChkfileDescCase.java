package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;

/**
 * Case for 勞保老年年金給付受理編審清單 - 編審資料 - 編審註記說明
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01ChkfileDescCase implements Serializable {
    private String chkCode; // 編審註記代碼
    private String chkCodePost; // 編審註記代號類型
    private String chkResult; // 編審註記名稱
    private String keyValue;// 關鍵欄位值

    public OldAgeReviewRpt01ChkfileDescCase() {

    }

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

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

}
