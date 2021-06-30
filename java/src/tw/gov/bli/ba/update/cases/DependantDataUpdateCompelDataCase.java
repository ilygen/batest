package tw.gov.bli.ba.update.cases;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.PkeyField;

/**
 * Case for 眷屬資料更正 不合格年月資料
 * 
 * @author Kiyomi
 */
public class DependantDataUpdateCompelDataCase {
    private String apNo;// 受理編號
    private String seqNo;// 受款人序號
    private BigDecimal compelNo;// 序號
    private String compelSdate;// 強迫不合格起始年月
    private String compelEdate;// 強迫不合格結束年月
    private String compelDesc;// 強迫不合格原因
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間

    // 頁面顯示轉換
    // [
    public String getCompelSdateChineseStr() {
        // 若日期為6碼(西元年月)才轉
        if (StringUtils.isNotBlank(compelSdate) && compelSdate.length() == 6) {
            return (DateUtility.changeWestYearMonthType(compelSdate));
        }
        return compelSdate;
    }

    public String getCompelEdateChineseStr() {
        // 若日期為6碼(西元年月)才轉
        if (StringUtils.isNotBlank(compelEdate) && compelEdate.length() == 6) {
            return (DateUtility.changeWestYearMonthType(compelEdate));
        }
        return compelEdate;
    }

    // ]

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

    public BigDecimal getCompelNo() {
        return compelNo;
    }

    public void setCompelNo(BigDecimal compelNo) {
        this.compelNo = compelNo;
    }

    public String getCompelSdate() {
        return compelSdate;
    }

    public void setCompelSdate(String compelSdate) {
        this.compelSdate = compelSdate;
    }

    public String getCompelEdate() {
        return compelEdate;
    }

    public void setCompelEdate(String compelEdate) {
        this.compelEdate = compelEdate;
    }

    public String getCompelDesc() {
        return compelDesc;
    }

    public void setCompelDesc(String compelDesc) {
        this.compelDesc = compelDesc;
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

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }
}
