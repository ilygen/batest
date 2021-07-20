package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;

/**
 * 遺屬強迫不合格記錄檔
 * 
 * @author Rickychi
 */
public class Bacompelnopay implements Serializable {
    @PkeyField("APNO")
    private String apNo;// 受理編號
    @PkeyField("SEQNO")
    private String seqNo;// 受款人序號
    @PkeyField("COMPELNO")
    private BigDecimal compelNo;// 序號
    private String compelSdate;// 強迫不合格起始年月
    private String compelEdate;// 強迫不合格結束年月
    private String compelDesc;// 強迫不合格原因
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間
    
    private BigDecimal dataCount;// 強迫不合格記錄筆數    

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
    
    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }    
}
