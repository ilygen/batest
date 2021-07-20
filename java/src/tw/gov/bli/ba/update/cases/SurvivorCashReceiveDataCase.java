package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 遺屬年金應收收回處理
 * 
 * @author Noctis
 */
public class SurvivorCashReceiveDataCase implements Serializable {
    private BigDecimal baunacpdtlId; // 資料列編號(應收帳務明細編號)
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
    private BigDecimal available_Money;// //可用餘額
    private BigDecimal affairreCount;// 業務單位辦理收回次數
    private String recm;// 應收月份
    private String camField;// 關鍵編號欄位
    // For CancelCashReceive
    private String insKdCash; // 保險別
    // BAACPDTL bli_Account_Code cashAmt
    private String bliAccountCode; // 局帳戶代號
    private BigDecimal cashAmt; // 現金金額
    private String indexNo;// 序號
    private BigDecimal recAmt; // 應收金額

    private Long retrieveMoney;// 收回金額
    private String payKind;

    // For BAUNACPDTL
    private String apNo;
    private String seqNo;
    private String issuYm;
    private String payYm;
    private BigDecimal recRem;
    private BigDecimal baacpdtlId; //

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

    public Long getRetrieveMoney() {
        return retrieveMoney;
    }

    public void setRetrieveMoney(Long retrieveMoney) {
        this.retrieveMoney = retrieveMoney;
    }

    public String getInsKdCash() {
        return insKdCash;
    }

    public void setInsKdCash(String insKdCash) {
        this.insKdCash = insKdCash;
    }

    public String getBliAccountCode() {
        return bliAccountCode;
    }

    public void setBliAccountCode(String bliAccountCode) {
        this.bliAccountCode = bliAccountCode;
    }

    public BigDecimal getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(BigDecimal cashAmt) {
        this.cashAmt = cashAmt;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public BigDecimal getBaunacpdtlId() {
        return baunacpdtlId;
    }

    public void setBaunacpdtlId(BigDecimal baunacpdtlId) {
        this.baunacpdtlId = baunacpdtlId;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
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

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public BigDecimal getBaacpdtlId() {
        return baacpdtlId;
    }

    public void setBaacpdtlId(BigDecimal baacpdtlId) {
        this.baacpdtlId = baacpdtlId;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

}
