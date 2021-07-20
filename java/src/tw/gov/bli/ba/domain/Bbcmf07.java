package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.Table;

/**
 * 醫療院所參數檔
 * 
 * @author Evelyn Hsu
 */
@Table("BBCMF07")
public class Bbcmf07 implements Serializable {
    private String insKd;//                
    private String pmTyp;// 參數類別
    private String hpCode;// 醫院代碼
    private String hpName;// 醫院名稱
    private String hpSnam;// 醫院簡稱
//    private String payTyp;// 付費方式
    private BigDecimal payAmt;// 每件查調費用
    private String hosCtp;// 特約類別
    private String eduMk;// 教學成本註記
    private String hosGrd;// 評鑑等級
    private String status;// 異動狀態

    public String getInsKd() {
        return insKd;
    }

    public void setInsKd(String insKd) {
        this.insKd = insKd;
    }

    public String getPmTyp() {
        return pmTyp;
    }

    public void setPmTyp(String pmTyp) {
        this.pmTyp = pmTyp;
    }

    public String getHpSnam() {
        return hpSnam;
    }

    public void setHpSnam(String hpSnam) {
        this.hpSnam = hpSnam;
    }

//    public String getPayTyp() {
//        return payTyp;
//    }
//
//    public void setPayTyp(String payTyp) {
//        this.payTyp = payTyp;
//    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getHosCtp() {
        return hosCtp;
    }

    public void setHosCtp(String hosCtp) {
        this.hosCtp = hosCtp;
    }

    public String getEduMk() {
        return eduMk;
    }

    public void setEduMk(String eduMk) {
        this.eduMk = eduMk;
    }

    public String getHosGrd() {
        return hosGrd;
    }

    public void setHosGrd(String hosGrd) {
        this.hosGrd = hosGrd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHpCode() {
        return hpCode;
    }

    public void setHpCode(String hpCode) {
        this.hpCode = hpCode;
    }

    public String getHpName() {
        return hpName;
    }

    public void setHpName(String hpName) {
        this.hpName = hpName;
    }

}
