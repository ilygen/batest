package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.LogField;

/**
 * 退現資料檔
 * 
 * @author Noctis
 */
public class Pfpccky implements Serializable {
    private String insKd;// 保險別
    private String bli_Account_Code;// 局帳戶代號
    private String bookedBook;// 入帳方式
    private String bkAccountDt;// 銀行入帳日期
    private String batchNo;// 批號
    private String serialNo;// 流水號
    private BigDecimal cash_Amt;// 現金金額
    private String divSeq;// 分割序號
    private String index_No;// 序號
    private String moveToCode;// 移至註記
    private String tempHandleNo;// 受理編號
    private String affairrePrno;// 業務單位辦理收回承辦人員
    private String affairredept;// 業務單位辦理收回科別
    private String affairredate;// 業務單位辦理收回承辦日期
    private BigDecimal available_Money;// 可用餘額
    private BigDecimal affairreCount;// 業務單位辦理收回次數
    private String recm;// 應收月份
    private String camField;// 關鍵編號欄位
    private String per_Unit_Name;
    private String source; // 來源
    private String seqNo;// 序號
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private String divMrk;
    private BigDecimal divAmount;
    private BigDecimal totalMoney;
    private String tempPayDte;

    public String getInsKd() {
        return insKd;
    }

    public void setInsKd(String insKd) {
        this.insKd = insKd;
    }

    public String getBli_Account_Code() {
        return bli_Account_Code;
    }

    public void setBli_Account_Code(String bli_Account_Code) {
        this.bli_Account_Code = bli_Account_Code;
    }

    public String getBookedBook() {
        return bookedBook;
    }

    public void setBookedBook(String bookedBook) {
        this.bookedBook = bookedBook;
    }

    public String getBkAccountDt() {
        return bkAccountDt;
    }

    public void setBkAccountDt(String bkAccountDt) {
        this.bkAccountDt = bkAccountDt;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getCash_Amt() {
        return cash_Amt;
    }

    public void setCash_Amt(BigDecimal cash_Amt) {
        this.cash_Amt = cash_Amt;
    }

    public String getDivSeq() {
        return divSeq;
    }

    public void setDivSeq(String divSeq) {
        this.divSeq = divSeq;
    }

    public String getIndex_No() {
        return index_No;
    }

    public void setIndex_No(String index_No) {
        this.index_No = index_No;
    }

    public String getMoveToCode() {
        return moveToCode;
    }

    public void setMoveToCode(String moveToCode) {
        this.moveToCode = moveToCode;
    }

    public String getTempHandleNo() {
        return tempHandleNo;
    }

    public void setTempHandleNo(String tempHandleNo) {
        this.tempHandleNo = tempHandleNo;
    }

    public String getAffairrePrno() {
        return affairrePrno;
    }

    public void setAffairrePrno(String affairrePrno) {
        this.affairrePrno = affairrePrno;
    }

    public String getAffairredept() {
        return affairredept;
    }

    public void setAffairredept(String affairredept) {
        this.affairredept = affairredept;
    }

    public String getAffairredate() {
        return affairredate;
    }

    public void setAffairredate(String affairredate) {
        this.affairredate = affairredate;
    }

    public BigDecimal getAvailable_Money() {
        return available_Money;
    }

    public void setAvailable_Money(BigDecimal available_Money) {
        this.available_Money = available_Money;
    }

    public BigDecimal getAffairreCount() {
        return affairreCount;
    }

    public void setAffairreCount(BigDecimal affairreCount) {
        this.affairreCount = affairreCount;
    }

    public String getRecm() {
        return recm;
    }

    public void setRecm(String recm) {
        this.recm = recm;
    }

    public String getCamField() {
        return camField;
    }

    public void setCamField(String camField) {
        this.camField = camField;
    }

    public String getPer_Unit_Name() {
        return per_Unit_Name;
    }

    public void setPer_Unit_Name(String per_Unit_Name) {
        this.per_Unit_Name = per_Unit_Name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
    
    public String getDivMrk() {
        return divMrk;
    }

    public void setDivMrk(String divMrk) {
        this.divMrk = divMrk;
    }
    
    public BigDecimal getDivAmount() {
        return divAmount;
    }

    public void setDivAmount(BigDecimal divAmount) {
        this.divAmount = divAmount;
    }
    
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
    
    public String getTempPayDte() {
        return tempPayDte;
    }

    public void setTempPayDte(String tempPayDte) {
        this.tempPayDte = tempPayDte;
    }

}
