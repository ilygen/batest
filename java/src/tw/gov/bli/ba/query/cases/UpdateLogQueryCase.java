package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 更正日誌查詢
 * 
 * @author Goston
 */
public class UpdateLogQueryCase implements Serializable {
    private String payCode; // 給付別
    private String updateDateBegin; // 處理時間 (起)
    private String updateDateEnd; // 處理時間 (迄)
    private String apNo; // 受理編號
    private String benIdnNo; // 身分證號
    private String updUser; // 更正人員

    private List<UpdateLogQueryDetailCase> updateLogList; // 更正紀錄檔

    /**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "老年年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "失能年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "遺屬年金";
        else
            return "";
    }

    /**
     * 處理時間 (起)
     * 
     * @return
     */
    public String getUpdateDateBeginString() {
        return DateUtility.formatChineseDateString(updateDateBegin, false);
    }

    /**
     * 處理時間 (迄)
     * 
     * @return
     */
    public String getUpdateDateEndString() {
        return DateUtility.formatChineseDateString(updateDateEnd, false);
    }

    /**
     * 取得 受理編號 顯示格式
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    /**
     * 建構子
     */
    public UpdateLogQueryCase() {
        updateLogList = new ArrayList<UpdateLogQueryDetailCase>();
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getUpdateDateBegin() {
        return updateDateBegin;
    }

    public void setUpdateDateBegin(String updateDateBegin) {
        this.updateDateBegin = updateDateBegin;
    }

    public String getUpdateDateEnd() {
        return updateDateEnd;
    }

    public void setUpdateDateEnd(String updateDateEnd) {
        this.updateDateEnd = updateDateEnd;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public List<UpdateLogQueryDetailCase> getUpdateLogList() {
        return updateLogList;
    }

    public void setUpdateLogList(List<UpdateLogQueryDetailCase> updateLogList) {
        this.updateLogList = updateLogList;
    }

}
