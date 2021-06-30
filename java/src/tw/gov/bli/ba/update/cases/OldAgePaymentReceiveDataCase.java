package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 老年年金應收收回處理
 * 
 * @author Noctis
 */
public class OldAgePaymentReceiveDataCase implements Serializable {
    private static final long serialVersionUID = -5056817223316820001L;

    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String evtName; // 事故者姓名
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String evtJobDate;// 事故日期
    private String payKind; // 給付種類
    private String issuYm; // 核定年月
    private String bkAccountDt; // 銀行入帳日期
    private String payYm;
    private String tempPayDte;

    private BigDecimal baunacpdtlId; // 資料列編號(應收帳務明細編號)
    private String insKd;// 保險別
    private String bli_Account_Code;// 局帳戶代號
    private String bookedBook;// 入帳方式
    private String batchNo;// 批號
    private String serialNo;// 流水號
    private BigDecimal cash_Amt;// 現金金額
    private String divSeq;// 分割序號
    private String index_No;// 序號

    private String bliAccountCode; // 局帳戶代號
    private BigDecimal cashAmt; // 現金金額
    private String indexNo;// 序號

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    // 給付別中文
    public String getApNoKindStrDisplay() {
        String apNoKind = "";
        String apNoKindStr = "";
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            apNoKind = getApNo().substring(0, 1);
        if (apNoKind.equals("L")) {
            apNoKindStr = "老年";
            return apNoKindStr;
        }
        else if (apNoKind.equals("K")) {
            apNoKindStr = "失能";
            return apNoKindStr;
        }
        else if (apNoKind.equals("S")) {
            apNoKindStr = "遺屬";
            return apNoKindStr;
        }
        else
            return "";
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate())) {
            return DateUtility.changeDateType(getEvtBrDate());
        }
        else {
            return "";
        }
    }

    public String getEvtJobDateStr() {
        if (StringUtils.isNotBlank(getEvtJobDate())) {
            return DateUtility.changeDateType(getEvtJobDate());
        }
        else {
            return "";
        }
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

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
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

    public String getTempPayDte() {
        return tempPayDte;
    }

    public void setTempPayDte(String tempPayDte) {
        this.tempPayDte = tempPayDte;
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

    public BigDecimal getBaunacpdtlId() {
        return baunacpdtlId;
    }

    public void setBaunacpdtlId(BigDecimal baunacpdtlId) {
        this.baunacpdtlId = baunacpdtlId;
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

}
