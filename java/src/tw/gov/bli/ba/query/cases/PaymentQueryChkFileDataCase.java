package tw.gov.bli.ba.query.cases;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 受款人資料 - 編審註記資料
 * 
 * @author Rickychi
 */
public class PaymentQueryChkFileDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String seqNo; // 序號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String chkCodePost; // 編審註記
    private String chkResult; // 編審註記說明

    // 頁面顯示轉換
    // [
    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm())) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return getIssuYm();
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm())) {
            return DateUtility.changeWestYearMonthType(getPayYm());
        }
        else {
            return getPayYm();
        }
    }

    // ]
    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
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
