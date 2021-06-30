package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 改匯資料檔
 * 
 * @author Rickychi
 */
@Table("BAREGIVEDTL")
public class Baregivedtl implements Serializable {
    @PkeyField("APNO")
    private String apNo;// 受理編號
    @PkeyField("SEQNO")
    private String seqNo;// 受款人序
    @PkeyField("BRISSUYM")
    private String brIssuYm;// 退匯核定年月
    @PkeyField("PAYYM")
    private String payYm;// 給付年月
    @PkeyField("TRANSACTIONID")
    private String transActionId;// 交易編號

    private String oriIssuYm;// 原核定年月
    private String issuYm;// 核定年月
    private String insKd;// 保險別
    private String issuKind;// 核發種類
    private String mk;// 退改匯別
    private String brMk;// 退匯狀態
    private String afMk;// 退改匯狀態
    private String workMk;// 處理註記
    private String afChkDate;// 改匯初核日期
    private String afPayDate;// 出納核付日期
    private String bliAccount;// 退匯局帳戶代號
    private String benTyp;// 受款人種類
    private String benIds;// 受益人社福識別碼
    private String benIdn;// 受款人身分證號
    private String benName;// 受款人姓名
    private String issuTyp;// 核付分類
    private String payTyp;// 核發方式/給付方式
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payEeacc;// 銀行帳號
    private String accIdn;// 戶名IDN
    private String accName;// 戶名
    private String accSeqNo;// 被共同具領之受款人員序號
    private String commZip;// 郵遞區號
    private String commAddr;// 地址
    private String commTel;// 電話
    private BigDecimal remitAmt;// 退匯金額
    private BigDecimal recAmt;// 收回金額
    private String reChkMan;// 複核人員
    private String reChkDate;// 複核日期
    private String exeDate;// 確認人員
    private String exeMan;// 確認日期
    private String actTitleApCode;// 會計科目案類代號
    private String cprnDate;// 清單印表日期
    private String rptPage;// 清單印表頁次
    private String rptRows;// 清單印表行次
    private String genmedDate;// 產出媒體日期
    private String procUser;// 作業人員代號
    private String procDeptId;// 作業人員的部室別
    private String procIp;// 作業人員的IP
    private String updTime;// 異動日期時間

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

    public String getBrIssuYm() {
        return brIssuYm;
    }

    public void setBrIssuYm(String brIssuYm) {
        this.brIssuYm = brIssuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getTransActionId() {
        return transActionId;
    }

    public void setTransActionId(String transActionId) {
        this.transActionId = transActionId;
    }

    public String getOriIssuYm() {
        return oriIssuYm;
    }

    public void setOriIssuYm(String oriIssuYm) {
        this.oriIssuYm = oriIssuYm;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getInsKd() {
        return insKd;
    }

    public void setInsKd(String insKd) {
        this.insKd = insKd;
    }

    public String getIssuKind() {
        return issuKind;
    }

    public void setIssuKind(String issuKind) {
        this.issuKind = issuKind;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getBrMk() {
        return brMk;
    }

    public void setBrMk(String brMk) {
        this.brMk = brMk;
    }

    public String getAfMk() {
        return afMk;
    }

    public void setAfMk(String afMk) {
        this.afMk = afMk;
    }

    public String getWorkMk() {
        return workMk;
    }

    public void setWorkMk(String workMk) {
        this.workMk = workMk;
    }

    public String getAfChkDate() {
        return afChkDate;
    }

    public void setAfChkDate(String afChkDate) {
        this.afChkDate = afChkDate;
    }

    public String getAfPayDate() {
        return afPayDate;
    }

    public void setAfPayDate(String afPayDate) {
        this.afPayDate = afPayDate;
    }

    public String getBliAccount() {
        return bliAccount;
    }

    public void setBliAccount(String bliAccount) {
        this.bliAccount = bliAccount;
    }

    public String getBenTyp() {
        return benTyp;
    }

    public void setBenTyp(String benTyp) {
        this.benTyp = benTyp;
    }

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
    }

    public String getBenIdn() {
        return benIdn;
    }

    public void setBenIdn(String benIdn) {
        this.benIdn = benIdn;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getIssuTyp() {
        return issuTyp;
    }

    public void setIssuTyp(String issuTyp) {
        this.issuTyp = issuTyp;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
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

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
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

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
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

    public String getCommTel() {
        return commTel;
    }

    public void setCommTel(String commTel) {
        this.commTel = commTel;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public String getReChkMan() {
        return reChkMan;
    }

    public void setReChkMan(String reChkMan) {
        this.reChkMan = reChkMan;
    }

    public String getReChkDate() {
        return reChkDate;
    }

    public void setReChkDate(String reChkDate) {
        this.reChkDate = reChkDate;
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

    public String getActTitleApCode() {
        return actTitleApCode;
    }

    public void setActTitleApCode(String actTitleApCode) {
        this.actTitleApCode = actTitleApCode;
    }

    public String getCprnDate() {
        return cprnDate;
    }

    public void setCprnDate(String cprnDate) {
        this.cprnDate = cprnDate;
    }

    public String getRptPage() {
        return rptPage;
    }

    public void setRptPage(String rptPage) {
        this.rptPage = rptPage;
    }

    public String getRptRows() {
        return rptRows;
    }

    public void setRptRows(String rptRows) {
        this.rptRows = rptRows;
    }

    public String getGenmedDate() {
        return genmedDate;
    }

    public void setGenmedDate(String genmedDate) {
        this.genmedDate = genmedDate;
    }

    public String getProcUser() {
        return procUser;
    }

    public void setProcUser(String procUser) {
        this.procUser = procUser;
    }

    public String getProcDeptId() {
        return procDeptId;
    }

    public void setProcDeptId(String procDeptId) {
        this.procDeptId = procDeptId;
    }

    public String getProcIp() {
        return procIp;
    }

    public void setProcIp(String procIp) {
        this.procIp = procIp;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

}
