package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 紓困貸款呆帳檔 (<code>LNMD</code>) <br>
 * 
 * 此檔資料是轉下來的
 * @author swim
 */
public class Lnmd implements Serializable {
    private String idn_Aa;
    private String brdte_Aa;
    private String name_Aa;
    private String pbmk;
    private String uno;
    private String idn_Bb;
    private String brdte_Bb;
    private String name_Bb;
    private String rcvNo;
    private BigDecimal year;
    private BigDecimal amt1;
    private BigDecimal amt2;
    private BigDecimal amt3;
    private BigDecimal amt4;
    private String gdate;
    private String efdate;
    private String kno;
    private String udate;
    private String tare;
    private String bank;
    private String filerr_14;
    public String getIdn_Aa() {
        return idn_Aa;
    }
    public void setIdn_Aa(String idn_Aa) {
        this.idn_Aa = idn_Aa;
    }
    public String getBrdte_Aa() {
        return brdte_Aa;
    }
    public void setBrdte_Aa(String brdte_Aa) {
        this.brdte_Aa = brdte_Aa;
    }
    public String getName_Aa() {
        return name_Aa;
    }
    public void setName_Aa(String name_Aa) {
        this.name_Aa = name_Aa;
    }
    public String getPbmk() {
        return pbmk;
    }
    public void setPbmk(String pbmk) {
        this.pbmk = pbmk;
    }
    public String getUno() {
        return uno;
    }
    public void setUno(String uno) {
        this.uno = uno;
    }
    public String getIdn_Bb() {
        return idn_Bb;
    }
    public void setIdn_Bb(String idn_Bb) {
        this.idn_Bb = idn_Bb;
    }
    public String getBrdte_Bb() {
        return brdte_Bb;
    }
    public void setBrdte_Bb(String brdte_Bb) {
        this.brdte_Bb = brdte_Bb;
    }
    public String getName_Bb() {
        return name_Bb;
    }
    public void setName_Bb(String name_Bb) {
        this.name_Bb = name_Bb;
    }
    public String getRcvNo() {
        return rcvNo;
    }
    public void setRcvNo(String rcvNo) {
        this.rcvNo = rcvNo;
    }
    public BigDecimal getYear() {
        return year;
    }
    public void setYear(BigDecimal year) {
        this.year = year;
    }
    public BigDecimal getAmt1() {
        return amt1;
    }
    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }
    public BigDecimal getAmt2() {
        return amt2;
    }
    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }
    public BigDecimal getAmt3() {
        return amt3;
    }
    public void setAmt3(BigDecimal amt3) {
        this.amt3 = amt3;
    }
    public BigDecimal getAmt4() {
        return amt4;
    }
    public void setAmt4(BigDecimal amt4) {
        this.amt4 = amt4;
    }
    public String getGdate() {
        return gdate;
    }
    public void setGdate(String gdate) {
        this.gdate = gdate;
    }
    public String getEfdate() {
        return efdate;
    }
    public void setEfdate(String efdate) {
        this.efdate = efdate;
    }
    public String getKno() {
        return kno;
    }
    public void setKno(String kno) {
        this.kno = kno;
    }
    public String getUdate() {
        return udate;
    }
    public void setUdate(String udate) {
        this.udate = udate;
    }
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getTare() {
        return tare;
    }
    public void setTare(String tare) {
        this.tare = tare;
    }
    public String getFilerr_14() {
        return filerr_14;
    }
    public void setFilerr_14(String filerr_14) {
        this.filerr_14 = filerr_14;
    }
    
}
