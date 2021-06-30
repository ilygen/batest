package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 遺屬眷屬重殘期間檔(<code>BAHANDICAPTERM</code>)
 * 
 * @author Eddie.Huang
 */
@Table("BAHANDICAPTERM")
public class Bahandicapterm implements Serializable{
    @PkeyField("APNO")
    private String apNo;// 受理編號
    @PkeyField("SEQNO")
    private String seqNo;// 受款人序號
    @PkeyField("TERMNO")
    private BigDecimal termNo;// 序號
    
    private String handicapSdate;// 重殘起始年月
    private String handicapEdate;// 重殘結束年月
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間
    
    // Field not in BAHANDICAPTERM
    // [
    private BigDecimal handicapDataCount;// 重殘記錄筆數

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

    public BigDecimal getTermNo() {
        return termNo;
    }

    public void setTermNo(BigDecimal termNo) {
        this.termNo = termNo;
    }
    
    //重殘起始年月
    public String getHandicapSdateChineseStr() {
        //若日期為6碼(西元年月)才轉
        if(StringUtils.isNotBlank(handicapSdate) && handicapSdate.length() == 6){
            return (DateUtility.changeDateType(handicapSdate + "01")).substring(0, 5);
        }
        return handicapSdate;
    }

    public void setHandicapSdate(String handicapSdate) {
        this.handicapSdate = handicapSdate;
    }

    //重殘結束年月
    public String getHandicapEdateChineseStr() {
        //若日期為6碼(西元年月)才轉
        if(StringUtils.isNotBlank(handicapEdate) && handicapEdate.length() == 6){
            return (DateUtility.changeDateType(handicapEdate + "01")).substring(0, 5);
        }
        return handicapEdate;
    }

    public String getHandicapSdate() {
        return handicapSdate;
    }

    public String getHandicapEdate() {
        return handicapEdate;
    }

    public void setHandicapEdate(String handicapEdate) {
        this.handicapEdate = handicapEdate;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtuser) {
        this.crtUser = crtuser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crttime) {
        this.crtTime = crttime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String upduser) {
        this.updUser = upduser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updtime) {
        this.updTime = updtime;
    }

    public BigDecimal getHandicapDataCount() {
        return handicapDataCount;
    }

    public void setHandicapDataCount(BigDecimal handicapDataCount) {
        this.handicapDataCount = handicapDataCount;
    }
    
    // ]
}
