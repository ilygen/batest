package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退回現金業務單位使用紀錄檔
 * 
 * @author Kiyomi
 */
public class Bafailurelist implements Serializable {
    private String divSeq;// 分割序號
    private String tempHandleNo;// 受理編號
    private String payCode;// 給付別
    private String chkDate;// 核收日期
    private String remark;// 備註
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private BigDecimal available_Money; // 可用餘額

    public String getDivSeq() {
        return divSeq;
    }

    public void setDivSeq(String divSeq) {
        this.divSeq = divSeq;
    }

    public String getTempHandleNo() {
        return tempHandleNo;
    }

    public void setTempHandleNo(String tempHandleNo) {
        this.tempHandleNo = tempHandleNo;
    }
    
    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }
    
    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }
    
    public BigDecimal getAvailable_Money() {
        return available_Money;
    }

    public void setAvailable_Money(BigDecimal available_Money) {
        this.available_Money = available_Money;
    }

}
