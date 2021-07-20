package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 查調病歷檔
 * 
 * @author Evelyn Hsu
 */
@Table("BABAN")
public class Baban implements Serializable{
    
    @PkeyField("APNO")
    private String apNo;         //受理編號
    @PkeyField("PRODTE")
    private String proDte;      //處理日期
    @PkeyField("CLINNO")
    private String clinNo;       //醫院代號    
    
    private String idNo;         //身分證號    
    private String name;       //被保險人姓名
    private String payType;      //給付種類    
    private String clinNm;       //醫院名稱    
    private String payWay;       //付款方式    
    private BigDecimal expEns;   //查調費      
    private String invRpn;       //查調文號    
    private String invDte;       //查調日期    
    private String invPno;       //查調承辦人員
    private String repDte;       //覆文日期    
    private String payRpn;       //付費文號    
    private String slDte;        //發文日期    
    private String payPno;       //付費承辦人員
    private String advDte;       //預付日期    
    private String payMk;        //付費註記    
    private String appDte;       //核付日期    
    private String clDte;        //收據銷帳日期

    public Baban(){
        
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getProDte() {
        return proDte;
    }

    public void setProDte(String proDte) {
        this.proDte = proDte;
    }

    public String getClinNo() {
        return clinNo;
    }

    public void setClinNo(String clinNo) {
        this.clinNo = clinNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getClinNm() {
        return clinNm;
    }

    public void setClinNm(String clinNm) {
        this.clinNm = clinNm;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public BigDecimal getExpEns() {
        return expEns;
    }

    public void setExpEns(BigDecimal expEns) {
        this.expEns = expEns;
    }

    public String getInvRpn() {
        return invRpn;
    }

    public void setInvRpn(String invRpn) {
        this.invRpn = invRpn;
    }

    public String getInvDte() {
        return invDte;
    }

    public void setInvDte(String invDte) {
        this.invDte = invDte;
    }

    public String getInvPno() {
        return invPno;
    }

    public void setInvPno(String invPno) {
        this.invPno = invPno;
    }

    public String getRepDte() {
        return repDte;
    }

    public void setRepDte(String repDte) {
        this.repDte = repDte;
    }

    public String getPayRpn() {
        return payRpn;
    }

    public void setPayRpn(String payRpn) {
        this.payRpn = payRpn;
    }

    public String getSlDte() {
        return slDte;
    }

    public void setSlDte(String slDte) {
        this.slDte = slDte;
    }

    public String getPayPno() {
        return payPno;
    }

    public void setPayPno(String payPno) {
        this.payPno = payPno;
    }

    public String getAdvDte() {
        return advDte;
    }

    public void setAdvDte(String advDte) {
        this.advDte = advDte;
    }

    public String getPayMk() {
        return payMk;
    }

    public void setPayMk(String payMk) {
        this.payMk = payMk;
    }

    public String getAppDte() {
        return appDte;
    }

    public void setAppDte(String appDte) {
        this.appDte = appDte;
    }

    public String getClDte() {
        return clDte;
    }

    public void setClDte(String clDte) {
        this.clDte = clDte;
    }
    
    


}
