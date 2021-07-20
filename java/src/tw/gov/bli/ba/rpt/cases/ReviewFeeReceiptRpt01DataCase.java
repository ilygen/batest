package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 複檢費用申請作業 - 複檢費用審核給付清單資料 
 * 
 * @author Evelyn Hsu
 */

public class ReviewFeeReceiptRpt01DataCase implements Serializable{
    
    private String reApNo;                  // 複檢費用受理編號  
    private BigDecimal apSeq;               // 複檢費用申請序   
    private String recosDate;               // 複檢費用申請日期
    private BigDecimal reFees;              // 複檢費用
    private BigDecimal nonreFees;           // 非複檢必須費用 
    private BigDecimal reAmtPay;            // 實付金額
    private String hosId;                   // 醫院代碼
    private String hosName;                 // 醫院名稱
    private String benIdnNo;                // 受款人身分證號
    private String benName;                 // 受款人姓名
    private String benBrDate;               // 受款人出生日期
    private String benEvtRel;               // 關係
    private String tel1;                    // 電話1
    private String tel2;                    // 電話2
    private String commZip;                 // 郵遞區號
    private String commAddr;                // 通訊地址 
    private String payTyp;                  // 給付方式
    private String payBankId;               // 金融機構總代號
    private String bankName;                // 金融機構名稱  
    private String branchId;                // 分支代號
    private String payeeAcc;                // 銀行帳號    
    private String accIdn;                  // 戶名IDN
    private String accName;                 // 戶名              
    private String accRel;                  // 戶名與受益人關係
    private String grdIdnNo;                // 法定代理人身分證號
    private String grdName;                 // 法定代理人姓名
    private String grdBrDate;               // 法定代理人出生日期
    private String crtUser;                 // 鍵入人員
    private String updUser;                 // 更正人員
    private String notifyForm;              // 核定通知書格式
    
    private String evtName;                 // 事故者姓名
    private String evtIdnNo;                // 事故者身分證號
    private String evtBrDate;               // 事故者出生日期
    private String evtAge;                  // 事故者申請時年齡
    
    
    /**
     * 事故者出生日期
     * 
     * @return
     */
    public String getEvtBrDateString() {
        if (StringUtils.length(evtBrDate) == 8) {
            String birthday = DateUtility.changeDateType(evtBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "民國前 " : "民國 ") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), true);
        }
        else {
            return StringUtils.defaultString(evtBrDate);
        }
    }
    
    /**
     * 受益人出生日期
     * 
     * @return
     */
    public String getBenBrDateString() {
        if (StringUtils.length(benBrDate) == 8) {
            String birthday = DateUtility.changeDateType(benBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "-" : "") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), false);
        }
        else {
            return StringUtils.defaultString(benBrDate);
        }
    }
    
    /**
     * 法定代理人出生日期
     * 
     * @return
     */
    public String getGrdBrDateString() {
        if (StringUtils.length(grdBrDate) == 8) {
            String birthday = DateUtility.changeDateType(grdBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "-" : "") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), false);
        }
        else {
            return "";
        }
    }
    
    
    
    // 核定通知書
    // [
    private ReviewFeeReceiptNotifyDataCase notifyData;
    // ]
    
    public ReviewFeeReceiptRpt01DataCase(){
        
    }

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

    public String getRecosDate() {
        return recosDate;
    }

    public void setRecosDate(String recosDate) {
        this.recosDate = recosDate;
    }

    public BigDecimal getReFees() {
        return reFees;
    }

    public void setReFees(BigDecimal reFees) {
        this.reFees = reFees;
    }

    public BigDecimal getNonreFees() {
        return nonreFees;
    }

    public void setNonreFees(BigDecimal nonreFees) {
        this.nonreFees = nonreFees;
    }

    public BigDecimal getReAmtPay() {
        return reAmtPay;
    }

    public void setReAmtPay(BigDecimal reAmtPay) {
        this.reAmtPay = reAmtPay;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getAccIdn() {
        return accIdn;
    }

    public void setAccIdn(String accIdn) {
        this.accIdn = accIdn;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
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

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getEvtAge() {
        return evtAge;
    }

    public void setEvtAge(String evtAge) {
        this.evtAge = evtAge;
    }

    public String getPayBankId() {
        return payBankId;
    }

    public void setPayBankId(String payBankId) {
        this.payBankId = payBankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public String getPayeeAcc() {
        return payeeAcc;
    }

    public void setPayeeAcc(String payeeAcc) {
        this.payeeAcc = payeeAcc;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ReviewFeeReceiptNotifyDataCase getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(ReviewFeeReceiptNotifyDataCase notifyData) {
        this.notifyData = notifyData;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }
    
    
    
    
    
    

}
