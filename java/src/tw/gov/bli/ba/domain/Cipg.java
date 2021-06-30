package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 被保險人投保薪資檔
 * 
 * @author Evelyn Hsu
 */

@Table("CIPG")
public class Cipg implements Serializable {

    @PkeyField("INTYP")
    private String inTyp;// 保險別

    @PkeyField("IDN")
    private String idn; // 身分證號

    private String avgTyp; // 投保薪資類別

    private String avgYm; // 投保年月

    private BigDecimal avgWg;// 月投保薪資

    private String proDte; // 資料擷取日

    private String dwMk; // 雙薪註記

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

    public String getAvgTyp() {
        return avgTyp;
    }

    public void setAvgTyp(String avgTyp) {
        this.avgTyp = avgTyp;
    }

    public String getAvgYm() {
        return avgYm;
    }

    public void setAvgYm(String avgYm) {
        this.avgYm = avgYm;
    }

    public BigDecimal getAvgWg() {
        return avgWg;
    }

    public void setAvgWg(BigDecimal avgWg) {
        this.avgWg = avgWg;
    }

    public String getProDte() {
        return proDte;
    }

    public void setProDte(String proDte) {
        this.proDte = proDte;
    }

    public String getDwMk() {
        return dwMk;
    }

    public void setDwMk(String dwMk) {
        this.dwMk = dwMk;
    }

}
