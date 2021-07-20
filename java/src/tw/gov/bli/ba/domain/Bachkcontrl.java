package tw.gov.bli.ba.domain;

import java.io.Serializable;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>)
 * 
 * @author Goston
 */
@Table("BACHKCONTRL")
public class Bachkcontrl implements Serializable {
	
	@PkeyField("APNO")@LogField("APNO")
    private String apNo; // 受理編號
    @PkeyField("SEQNO")@LogField("SEQNO")
    private String seqNo; // 序號
    @PkeyField("ISSUYM")@LogField("ISSUYM")
    private String issuYm; // 核定年月
    @PkeyField("PAYYM")@LogField("PAYYM")
    private String payYm; // 給付年月
    @PkeyField("CONTRLTYP")@LogField("CONTRLTYP")
    private String contrlTyp; // 管制對象類別
    @PkeyField("CHKCODE")@LogField("CHKCODE")
    private String chkCode; // 編審註記代號
    @LogField("VALISYM")
    private String valiSym; // 有效年月起
    @LogField("VALIEYM")
    private String valiEym; // 有效年月迄
    @LogField("KEYVALUE")
    private String keyValue; // 關鍵欄位值
    @LogField("CTRUSER")
    private String crtUser; // 新增者代號
    @LogField("CRTTIME")
    private String crtTime; // 新增日期時間
    
    
    public Bachkcontrl() {

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

    public String getContrlTyp() {
        return contrlTyp;
    }

    public void setContrlTyp(String contrlTyp) {
        this.contrlTyp = contrlTyp;
    }

    public String getChkCode() {
        return chkCode;
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }

    public String getValiSym() {
        return valiSym;
    }

    public void setValiSym(String valiSym) {
        this.valiSym = valiSym;
    }

    public String getValiEym() {
        return valiEym;
    }

    public void setValiEym(String valiEym) {
        this.valiEym = valiEym;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
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

}
