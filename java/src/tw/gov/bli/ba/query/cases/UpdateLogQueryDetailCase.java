package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Detail Case for 更正日誌查詢
 * 
 * @author Goston
 */
public class UpdateLogQueryDetailCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private BigDecimal baappbaseId; // 資料列編號
    private String status; // 狀態
    private String updTime; // 異動時間
    private String updUser; // 異動人員
    private String upCol; // 異動項目
    private String bvalue; // 改前內容
    private String avalue; // 改後內容

    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String benIdnNo; // 受益人身分證號

    /**
     * 狀態 - 中文說明
     * 
     * @return
     */
    public String getStatusString() {
        if (StringUtils.equalsIgnoreCase(status, "A"))
            return "新增";
        else if (StringUtils.equalsIgnoreCase(status, "U"))
            return "修改";
        else if (StringUtils.equalsIgnoreCase(status, "D"))
            return "刪除";
        else
            return "";
    }

    /**
     * 異動日期
     * 
     * @return
     */
    public String getUpdDateString() {
        if (StringUtils.length(updTime) == 14) {
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(DateUtility.splitWestDateTime(updTime, true)), false);
        }
        else {
            return "";
        }
    }

    /**
     * 異動時間
     * 
     * @return
     */
    public String getUpdTimeString() {
        if (StringUtils.length(updTime) == 14) {
            return DateUtility.formatTimeString(DateUtility.splitWestDateTime(updTime, false), false);
        }
        else {
            return "";
        }
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

    public UpdateLogQueryDetailCase() {
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpCol() {
        return upCol;
    }

    public void setUpCol(String upCol) {
        this.upCol = upCol;
    }

    public String getBvalue() {
        return bvalue;
    }

    public void setBvalue(String bvalue) {
        this.bvalue = bvalue;
    }

    public String getAvalue() {
        return avalue;
    }

    public void setAvalue(String avalue) {
        this.avalue = avalue;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

}
