package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.Table;

/**
 * 勞保複檢費用主檔
 * 
 * @author Evelyn Hsu
 */

@Table("BABCML7")
public class Babcml7 implements Serializable {
    private String reApNo; // 複檢費用受理編號
    private BigDecimal apSeq; // 複檢費用申請序
    private String apNo; // 受理編號
    private String procStat; // 處理狀態
    private String inreDate; // 通知複檢日期
    private String reasCode; // 複檢原因
    private String hosId; // 醫療院所代碼
    private String recosDate; // 複檢費用申請日期
    private BigDecimal reNum; // 複檢門診次數
    private BigDecimal rehpStay; // 複檢住院天數
    private BigDecimal reFees; // 複檢費用
    private BigDecimal nonreFees; // 非複檢必須費用
    private BigDecimal reAmtPay; // 複檢實付金額
    private String notifyForm; // 核定通知書格式
    private String benIdnNo; // 受益人身分證號
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
    private String benSex; // 受益人性別
    private String benNationTyp; // 受益人國籍別
    private String benNationCode; // 受益人國籍
    private String benEvtRel; // 受益人與事故者關係
    private String tel1; // 電話1
    private String tel2; // 電話2
    private String commTyp; // 通訊地址別
    private String commZip; // 通訊郵遞區號
    private String commAddr; // 通訊地址
    private String payTyp; // 給付方式
    private String bankName; // 金融機構名稱
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payeeAcc; // 銀行帳號
    private String accIdn; // 戶名IDN
    private String accName; // 戶名
    private String accRel; // 戶名與受益人關係
    private BigDecimal mitRate; // 匯款匯費
    private String grdIdnNo; // 法定代理人身分證號
    private String grdName; // 法定代理人姓名
    private String grdBrDate; // 法定代理人出生日期
    private String mexcLvl; // 應決行層級
    private String realexcLvl; // 實際決行層級
    private String chkDate; // 審核日期
    private String chkMan; // 審核人員
    private String rechkDate; // 複核日期
    private String rechkMan; // 複核人員
    private String exeDate; // 決行日期
    private String exeMan; // 決行人員
    private String promoteUser; // 承辦者代號
    private String chkStat; // 初核狀況
    private String rechkStat; // 複核狀況
    private String conMk; // 確認註記
    private String conDate; // 確認日期
    private String conMan; // 確認人員
    private String rejMk; // 退件原因註記
    private String drDate; // 製票日期
    private String drNo; // 製票編號
    private String payDate; // 核付日期
    private String rpayDate; // 入帳日期
    private String rtDate; // 退匯確認日期
    private String repDate; // 改匯日期
    private String stexpndMk; // 止付註記
    private String stexpndReason; // 止付原因
    private String stexpndDate; // 止付日期
    private String bureauAcc; // 局帳號
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間

    // Field not in BABCML7
    // [
    // for ReviewFeeReceipt
    private String evtName; // 事故者姓名
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String evtAge; // 事故者申請時年齡
    private String benNationName; // 受款人國籍名稱

    private String reasName; // 複檢原因 - 中文
    private String hosName; // 醫療院所 - 中文
    private String benEvtRelName; // 受益人與事故者關係 - 中文
    private String payTypName; // 給付方式 - 中文

    // ]
    // Field not in BABCML7

    public Babcml7() {

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

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getInreDate() {
        return inreDate;
    }

    public void setInreDate(String inreDate) {
        this.inreDate = inreDate;
    }

    public String getReasCode() {
        return reasCode;
    }

    public void setReasCode(String reasCode) {
        this.reasCode = reasCode;
    }

    public String getHosId() {
        return hosId;
    }

    public void setHosId(String hosId) {
        this.hosId = hosId;
    }

    public String getRecosDate() {
        return recosDate;
    }

    public void setRecosDate(String recosDate) {
        this.recosDate = recosDate;
    }

    public BigDecimal getReNum() {
        return reNum;
    }

    public void setReNum(BigDecimal reNum) {
        this.reNum = reNum;
    }

    public BigDecimal getRehpStay() {
        return rehpStay;
    }

    public void setRehpStay(BigDecimal rehpStay) {
        this.rehpStay = rehpStay;
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

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
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

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
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

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getPayeeAcc() {
        return payeeAcc;
    }

    public void setPayeeAcc(String payeeAcc) {
        this.payeeAcc = payeeAcc;
    }

    public String getAccIdn() {
        return accIdn;
    }

    public void setAccIdn(String accIdn) {
        this.accIdn = accIdn;
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

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
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

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public String getRealexcLvl() {
        return realexcLvl;
    }

    public void setRealexcLvl(String realexcLvl) {
        this.realexcLvl = realexcLvl;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getRechkDate() {
        return rechkDate;
    }

    public void setRechkDate(String rechkDate) {
        this.rechkDate = rechkDate;
    }

    public String getRechkMan() {
        return rechkMan;
    }

    public void setRechkMan(String rechkMan) {
        this.rechkMan = rechkMan;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getExeMan() {
        return exeMan;
    }

    public void setExeMan(String exeMan) {
        this.exeMan = exeMan;
    }

    public String getPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
    }

    public String getChkStat() {
        return chkStat;
    }

    public void setChkStat(String chkStat) {
        this.chkStat = chkStat;
    }

    public String getRechkStat() {
        return rechkStat;
    }

    public void setRechkStat(String rechkStat) {
        this.rechkStat = rechkStat;
    }

    public String getConMk() {
        return conMk;
    }

    public void setConMk(String conMk) {
        this.conMk = conMk;
    }

    public String getConDate() {
        return conDate;
    }

    public void setConDate(String conDate) {
        this.conDate = conDate;
    }

    public String getConMan() {
        return conMan;
    }

    public void setConMan(String conMan) {
        this.conMan = conMan;
    }

    public String getRejMk() {
        return rejMk;
    }

    public void setRejMk(String rejMk) {
        this.rejMk = rejMk;
    }

    public String getDrDate() {
        return drDate;
    }

    public void setDrDate(String drDate) {
        this.drDate = drDate;
    }

    public String getDrNo() {
        return drNo;
    }

    public void setDrNo(String drNo) {
        this.drNo = drNo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getRpayDate() {
        return rpayDate;
    }

    public void setRpayDate(String rpayDate) {
        this.rpayDate = rpayDate;
    }

    public String getRtDate() {
        return rtDate;
    }

    public void setRtDate(String rtDate) {
        this.rtDate = rtDate;
    }

    public String getRepDate() {
        return repDate;
    }

    public void setRepDate(String repDate) {
        this.repDate = repDate;
    }

    public String getStexpndMk() {
        return stexpndMk;
    }

    public void setStexpndMk(String stexpndMk) {
        this.stexpndMk = stexpndMk;
    }

    public String getStexpndReason() {
        return stexpndReason;
    }

    public void setStexpndReason(String stexpndReason) {
        this.stexpndReason = stexpndReason;
    }

    public String getStexpndDate() {
        return stexpndDate;
    }

    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
    }

    public String getBureauAcc() {
        return bureauAcc;
    }

    public void setBureauAcc(String bureauAcc) {
        this.bureauAcc = bureauAcc;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
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

    public String getEvtAge() {
        return evtAge;
    }

    public void setEvtAge(String evtAge) {
        this.evtAge = evtAge;
    }

    public String getBenNationName() {
        return benNationName;
    }

    public void setBenNationName(String benNationName) {
        this.benNationName = benNationName;
    }

    public String getReasName() {
        return reasName;
    }

    public void setReasName(String reasName) {
        this.reasName = reasName;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getBenEvtRelName() {
        return benEvtRelName;
    }

    public void setBenEvtRelName(String benEvtRelName) {
        this.benEvtRelName = benEvtRelName;
    }

    public String getPayTypName() {
        return payTypName;
    }

    public void setPayTypName(String payTypName) {
        this.payTypName = payTypName;
    }

}
