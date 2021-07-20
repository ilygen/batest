package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 被保險人異動資料檔
 * 
 * @author Rickychi
 */
@Table("CIPT")
public class Cipt implements Serializable {
    @PkeyField("INTYP")
    private String inTyp;// 保險別

    @PkeyField("IDN")
    private String idn;// 身分證號

    @PkeyField("UNO")
    private String uno;// 保險證號

    @PkeyField("EFDTE")
    private String unoCk;// 保險證號檢查碼

    @PkeyField("SEQNO")
    private BigDecimal seqNo;// 序號

    private String txcd;// 異動代號
    private String efDte;// 生效日期
    private BigDecimal wage;// 投保薪資
    private String dept;// 工作部門註記
    private String bsmn;// 計費記號
    private String staff;// 處理人員代號
    private String sidMk;// 特殊身分註記
    private String tsMk;// 異動原因註記
    private String nrpMk;// 不退費註記
    private String fill;// 空白
    private String proDte;// 資料擷取日

    public String getInTyp() {
        return inTyp;
    }

    public void setInTyp(String inTyp) {
        this.inTyp = inTyp;
    }

    public String getIdn() {
        return idn;
    }

    public void setIdn(String idn) {
        this.idn = idn;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getUnoCk() {
        return unoCk;
    }

    public void setUnoCk(String unoCk) {
        this.unoCk = unoCk;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }

    public String getTxcd() {
        return txcd;
    }

    public void setTxcd(String txcd) {
        this.txcd = txcd;
    }

    public String getEfDte() {
        return efDte;
    }

    public void setEfDte(String efDte) {
        this.efDte = efDte;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getBsmn() {
        return bsmn;
    }

    public void setBsmn(String bsmn) {
        this.bsmn = bsmn;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getSidMk() {
        return sidMk;
    }

    public void setSidMk(String sidMk) {
        this.sidMk = sidMk;
    }

    public String getTsMk() {
        return tsMk;
    }

    public void setTsMk(String tsMk) {
        this.tsMk = tsMk;
    }

    public String getNrpMk() {
        return nrpMk;
    }

    public void setNrpMk(String nrpMk) {
        this.nrpMk = nrpMk;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getProDte() {
        return proDte;
    }

    public void setProDte(String proDte) {
        this.proDte = proDte;
    }

}
