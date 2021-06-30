package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoTable;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 遺屬在學期間檔 (<code>BASTUDTERM</code>)
 * 
 * @author Rickychi
 */
@Table("BASTUDTERM")
public class Bastudterm implements Serializable {
    @PkeyField("APNO")
    private String apNo;// 受理編號
    @PkeyField("SEQNO")
    private String seqNo;// 受款人序號
    @PkeyField("TERMNO")
    private BigDecimal termNo;// 序號
    
    private String studSdate;// 在學起始年月
    private String studEdate;// 在學結束年月
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間
    
    // Field not in BASTUDTERM
    // [
    private BigDecimal studDataCount;// 在學記錄筆數
    
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

    public BigDecimal getTermNo() {
        return termNo;
    }

    public void setTermNo(BigDecimal termNo) {
        this.termNo = termNo;
    }

    public String getStudSdate() {
        return studSdate;
    }
    
    //在學期間起月
    public String getStudSdateChineseStr() {
    	//若日期為6碼(西元年月)才轉
    	if(StringUtils.isNotBlank(studSdate) && studSdate.length() == 6){
    		return (DateUtility.changeDateType(studSdate + "01")).substring(0, 5);
    	}
        return studSdate;
    }

    public void setStudSdate(String studSdate) {
        this.studSdate = studSdate;
    }

    public String getStudEdate() {
        return studEdate;
    }
    
    //在學期間迄月
    public String getStudEdateChineseStr() {
    	//若日期為6碼(西元年月)才轉
    	if(StringUtils.isNotBlank(studEdate) && studEdate.length() == 6){
    		return (DateUtility.changeDateType(studEdate + "01")).substring(0, 5);
    	}
        return studEdate;
    }

    public void setStudEdate(String studEdate) {
        this.studEdate = studEdate;
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

    public BigDecimal getStudDataCount() {
        return studDataCount;
    }

    public void setStudDataCount(BigDecimal studDataCount) {
        this.studDataCount = studDataCount;
    }

}
