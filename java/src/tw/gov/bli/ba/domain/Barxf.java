package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Barxf implements Serializable {
    private String rxfApNo;// 受理編號-應收
    private String rxfIdnNo;// 身分證號-應收
    private String rxfBrDate;// 出生日期-應收
    private String rxfName;// 受款人姓名-應收
    private String apNo;// 受理編號-撥付
    private String seqNo;// 序號-撥付
    private String benIdnNo;// 受款人身分證號-撥付
    private String benBrDate;// 受款人出生日期-撥付
    private String benName;// 受款人姓名-撥付
    private String evtJobDate;// 事故日期-撥付
    private String disPayKind;// 給付種類-撥付
    private String sts;// 資料狀況
    private BigDecimal subAmt;// 應收未收餘額
    private BigDecimal rbAmt;// 同意扣減金額
    private String prSt;// 處理狀況
    private String prDte;// 處理日期
    private String prPno;// 處理人員
    private String prtMk;// 照會單列印註記
    private String dataKd;// 資料種類(保險別)

    // Field not in BARXF FOR 受理審核清單 另案扣減資料
    // [
    private String payYm;// 給付年月
    private BigDecimal amtTot;// 應收金額
    private BigDecimal rowNum;
    // ]

    public String getRxfApNo() {
        return rxfApNo;
    }

    public void setRxfApNo(String rxfApNo) {
        this.rxfApNo = rxfApNo;
    }

    public String getRxfIdnNo() {
        return rxfIdnNo;
    }

    public void setRxfIdnNo(String rxfIdnNo) {
        this.rxfIdnNo = rxfIdnNo;
    }

    public String getRxfBrDate() {
        return rxfBrDate;
    }

    public void setRxfBrDate(String rxfBrDate) {
        this.rxfBrDate = rxfBrDate;
    }

    public String getRxfName() {
        return rxfName;
    }

    public void setRxfName(String rxfName) {
        this.rxfName = rxfName;
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

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getDisPayKind() {
        return disPayKind;
    }

    public void setDisPayKind(String disPayKind) {
        this.disPayKind = disPayKind;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public BigDecimal getSubAmt() {
        return subAmt;
    }

    public void setSubAmt(BigDecimal subAmt) {
        this.subAmt = subAmt;
    }

    public BigDecimal getRbAmt() {
        return rbAmt;
    }

    public void setRbAmt(BigDecimal rbAmt) {
        this.rbAmt = rbAmt;
    }

    public String getPrSt() {
        return prSt;
    }

    public void setPrSt(String prSt) {
        this.prSt = prSt;
    }

    public String getPrDte() {
        return prDte;
    }

    public void setPrDte(String prDte) {
        this.prDte = prDte;
    }

    public String getPrPno() {
        return prPno;
    }

    public void setPrPno(String prPno) {
        this.prPno = prPno;
    }

    public String getPrtMk() {
        return prtMk;
    }

    public void setPrtMk(String prtMk) {
        this.prtMk = prtMk;
    }

    public String getDataKd() {
        return dataKd;
    }

    public void setDataKd(String dataKd) {
        this.dataKd = dataKd;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public BigDecimal getAmtTot() {
        return amtTot;
    }

    public void setAmtTot(BigDecimal amtTot) {
        this.amtTot = amtTot;
    }

    public BigDecimal getRowNum() {
        return rowNum;
    }

    public void setRowNum(BigDecimal rowNum) {
        this.rowNum = rowNum;
    }

}
