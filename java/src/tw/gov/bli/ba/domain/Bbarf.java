package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Bbarf implements Serializable{
    private String apNo;//受理編號        
    private String gvIdno;//受款人身分證號
    private String gvName;//受款人姓名
    private BigDecimal gvSum;//受款人數 
    private String bgnDate;//發生日期
    private BigDecimal chkAmt;//核定金額   
    private BigDecimal payAmt;//實付金額   
    private BigDecimal amtTot;//應收金額   
    private BigDecimal amtInc;//已收金額    
    private BigDecimal intTot;//應收利息 
    private BigDecimal intInc;//已收利息  
    private BigDecimal scaTot;//應收罰鍰   
    private BigDecimal scaInt;//已收罰鍰     
    private BigDecimal staTot;//總期數    
    private BigDecimal staInc;//已收期數   
    private BigDecimal staAmt;//每期金額    
    private String lstDate;//最終繳款日   
    private String adjcd;//收回原因    
    private String sts;//資料狀況    "1":應收未收  "2":催收款    "3":呆帳         
    private String endMk;// 完了註記    " ":未收訖    "1":部份收回  "9":已收訖      
    public String getApNo() {
        return apNo;
    }
    public void setApNo(String apNo) {
        this.apNo = apNo;
    }
    public String getGvIdno() {
        return gvIdno;
    }
    public void setGvIdno(String gvIdno) {
        this.gvIdno = gvIdno;
    }
    public String getGvName() {
        return gvName;
    }
    public void setGvName(String gvName) {
        this.gvName = gvName;
    }
    public BigDecimal getGvSum() {
        return gvSum;
    }
    public void setGvSum(BigDecimal gvSum) {
        this.gvSum = gvSum;
    }
    public String getBgnDate() {
        return bgnDate;
    }
    public void setBgnDate(String bgnDate) {
        this.bgnDate = bgnDate;
    }
    public BigDecimal getChkAmt() {
        return chkAmt;
    }
    public void setChkAmt(BigDecimal chkAmt) {
        this.chkAmt = chkAmt;
    }
    public BigDecimal getPayAmt() {
        return payAmt;
    }
    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }
    public BigDecimal getAmtTot() {
        return amtTot;
    }
    public void setAmtTot(BigDecimal amtTot) {
        this.amtTot = amtTot;
    }
    public BigDecimal getAmtInc() {
        return amtInc;
    }
    public void setAmtInc(BigDecimal amtInc) {
        this.amtInc = amtInc;
    }
    public BigDecimal getIntTot() {
        return intTot;
    }
    public void setIntTot(BigDecimal intTot) {
        this.intTot = intTot;
    }
    public BigDecimal getIntInc() {
        return intInc;
    }
    public void setIntInc(BigDecimal intInc) {
        this.intInc = intInc;
    }
    public BigDecimal getScaTot() {
        return scaTot;
    }
    public void setScaTot(BigDecimal scaTot) {
        this.scaTot = scaTot;
    }
    public BigDecimal getScaInt() {
        return scaInt;
    }
    public void setScaInt(BigDecimal scaInt) {
        this.scaInt = scaInt;
    }
    public BigDecimal getStaTot() {
        return staTot;
    }
    public void setStaTot(BigDecimal staTot) {
        this.staTot = staTot;
    }
    public BigDecimal getStaInc() {
        return staInc;
    }
    public void setStaInc(BigDecimal staInc) {
        this.staInc = staInc;
    }
    public BigDecimal getStaAmt() {
        return staAmt;
    }
    public void setStaAmt(BigDecimal staAmt) {
        this.staAmt = staAmt;
    }
    public String getLstDate() {
        return lstDate;
    }
    public void setLstDate(String lstDate) {
        this.lstDate = lstDate;
    }
    public String getAdjcd() {
        return adjcd;
    }
    public void setAdjcd(String adjcd) {
        this.adjcd = adjcd;
    }
    public String getSts() {
        return sts;
    }
    public void setSts(String sts) {
        this.sts = sts;
    }
    public String getEndMk() {
        return endMk;
    }
    public void setEndMk(String endMk) {
        this.endMk = endMk;
    }
}
