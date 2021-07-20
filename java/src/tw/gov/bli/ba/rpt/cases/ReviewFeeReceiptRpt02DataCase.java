package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 複檢費用申請作業 - 複檢費用核定通知書
 * 
 * @author Evelyn Hsu
 */
public class ReviewFeeReceiptRpt02DataCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;

    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序
    private String apNo; // 受理編號
    private String benName; // 受款人姓名
    private String commAddr; // 通訊地址
    private String notifyForm; // 核定通知書格式
    private BigDecimal reAmtPay; // 實付金額
    private BigDecimal nonreFees; // 非複檢必須費用
    private String payDate; // 核付日期
    private String commZip; // 通訊郵遞區號
    private String wordNo;// 發文字號
    private String wordDate;// 發文日期
    private String unit;// 承辦單位
    private String comTel;// 連絡電話
    private String name; // 受文者姓名
    private String progenitor; // 正本

    private String evtName; // 事故者姓名
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期

    // 核定通知書
    // [
    private ReviewFeeReceiptNotifyDataCase notifyData;
    // ]

    public String getReApNo() {
        return reApNo;
    }

    public void setReApNo(String reApNo) {
        this.reApNo = reApNo;
    }

    public BigDecimal getApSeq() {
        return apSeq;
    }

    public void setApSeq(BigDecimal apSeq) {
        this.apSeq = apSeq;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public BigDecimal getReAmtPay() {
        return reAmtPay;
    }

    public void setReAmtPay(BigDecimal reAmtPay) {
        this.reAmtPay = reAmtPay;
    }

    public BigDecimal getNonreFees() {
        return nonreFees;
    }

    public void setNonreFees(BigDecimal nonreFees) {
        this.nonreFees = nonreFees;
    }

    public ReviewFeeReceiptNotifyDataCase getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(ReviewFeeReceiptNotifyDataCase notifyData) {
        this.notifyData = notifyData;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getWordNo() {
        return wordNo;
    }

    public void setWordNo(String wordNo) {
        this.wordNo = wordNo;
    }

    public String getWordDate() {
        return wordDate;
    }

    public void setWordDate(String wordDate) {
        this.wordDate = wordDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComTel() {
        return comTel;
    }

    public void setComTel(String comTel) {
        this.comTel = comTel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getProgenitor() {
        return progenitor;
    }

    public void setProgenitor(String progenitor) {
        this.progenitor = progenitor;
    }

}
