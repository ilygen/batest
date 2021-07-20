package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 貸款主檔 (<code>LNM</code>) <br>
 * 注意: 此檔的日期格式皆為民國日期
 * 
 * @author Goston
 */
public class Lnm implements Serializable {
    private String bankCd; // 銀行別
    private String brNo; // 分行代號
    private String seqNo; // 流水號
    private String idnAply; // 身份證號
    private String brDteAply; // 出生日期
    private String nameAply; // 姓名
    private String pcodeAply; // 優先戶別
    private String dateAply; // 申請日期
    private BigDecimal moneyAply; // 申請金額
    private String telNo1; // 電話 1
    private String telNo2; // 電話 2
    private String acptDte; // 本局受理日期
    private String updateDte; // 更新日期
    private String edMrk; // 編審結果確認註記
    private String brDteLib; // 出生日期 (勞保)
    private String nameLib; // 姓名 (勞保)
    private String pcodeExm; // 優先戶別 (勞保)
    private String okExm; // 中籤註記
    private String dateExm; // 核定日期
    private BigDecimal moneyExm; // 核定金額
    private String accountPay; // 放款帳號
    private String datePay; // 撥款日期
    private BigDecimal moneyPay; // 撥款金額
    private BigDecimal loanTerm; // 貸款期限
    private BigDecimal aadeMoney; // 累計抵銷金額
    private BigDecimal adeTimes; // 抵銷次數
    private BigDecimal arInt; // 當年已繳利息金額
    private BigDecimal nrMoney; // 未還本金金額
    private BigDecimal owMoney; // 累計逾欠本金金額
    private BigDecimal owInt; // 累計逾欠利息金額
    private String achkDte; // 對帳日期
    private String pdate; // 轉催收日期
    private BigDecimal emrk; // 結案註記
    private String edate; // 結案日期
    private String mneq; // 金額不符註記
    private String idnLib; // 勞保身份證號
    private String citrmMrk;
    private String dup;
    private String filler05;

    public Lnm() {
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getBrNo() {
        return brNo;
    }

    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getIdnAply() {
        return idnAply;
    }

    public void setIdnAply(String idnAply) {
        this.idnAply = idnAply;
    }

    public String getBrDteAply() {
        return brDteAply;
    }

    public void setBrDteAply(String brDteAply) {
        this.brDteAply = brDteAply;
    }

    public String getNameAply() {
        return nameAply;
    }

    public void setNameAply(String nameAply) {
        this.nameAply = nameAply;
    }

    public String getPcodeAply() {
        return pcodeAply;
    }

    public void setPcodeAply(String pcodeAply) {
        this.pcodeAply = pcodeAply;
    }

    public String getDateAply() {
        return dateAply;
    }

    public void setDateAply(String dateAply) {
        this.dateAply = dateAply;
    }

    public BigDecimal getMoneyAply() {
        return moneyAply;
    }

    public void setMoneyAply(BigDecimal moneyAply) {
        this.moneyAply = moneyAply;
    }

    public String getTelNo1() {
        return telNo1;
    }

    public void setTelNo1(String telNo1) {
        this.telNo1 = telNo1;
    }

    public String getTelNo2() {
        return telNo2;
    }

    public void setTelNo2(String telNo2) {
        this.telNo2 = telNo2;
    }

    public String getAcptDte() {
        return acptDte;
    }

    public void setAcptDte(String acptDte) {
        this.acptDte = acptDte;
    }

    public String getUpdateDte() {
        return updateDte;
    }

    public void setUpdateDte(String updateDte) {
        this.updateDte = updateDte;
    }

    public String getEdMrk() {
        return edMrk;
    }

    public void setEdMrk(String edMrk) {
        this.edMrk = edMrk;
    }

    public String getBrDteLib() {
        return brDteLib;
    }

    public void setBrDteLib(String brDteLib) {
        this.brDteLib = brDteLib;
    }

    public String getNameLib() {
        return nameLib;
    }

    public void setNameLib(String nameLib) {
        this.nameLib = nameLib;
    }

    public String getPcodeExm() {
        return pcodeExm;
    }

    public void setPcodeExm(String pcodeExm) {
        this.pcodeExm = pcodeExm;
    }

    public String getOkExm() {
        return okExm;
    }

    public void setOkExm(String okExm) {
        this.okExm = okExm;
    }

    public String getDateExm() {
        return dateExm;
    }

    public void setDateExm(String dateExm) {
        this.dateExm = dateExm;
    }

    public BigDecimal getMoneyExm() {
        return moneyExm;
    }

    public void setMoneyExm(BigDecimal moneyExm) {
        this.moneyExm = moneyExm;
    }

    public String getAccountPay() {
        return accountPay;
    }

    public void setAccountPay(String accountPay) {
        this.accountPay = accountPay;
    }

    public String getDatePay() {
        return datePay;
    }

    public void setDatePay(String datePay) {
        this.datePay = datePay;
    }

    public BigDecimal getMoneyPay() {
        return moneyPay;
    }

    public void setMoneyPay(BigDecimal moneyPay) {
        this.moneyPay = moneyPay;
    }

    public BigDecimal getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(BigDecimal loanTerm) {
        this.loanTerm = loanTerm;
    }

    public BigDecimal getAadeMoney() {
        return aadeMoney;
    }

    public void setAadeMoney(BigDecimal aadeMoney) {
        this.aadeMoney = aadeMoney;
    }

    public BigDecimal getAdeTimes() {
        return adeTimes;
    }

    public void setAdeTimes(BigDecimal adeTimes) {
        this.adeTimes = adeTimes;
    }

    public BigDecimal getArInt() {
        return arInt;
    }

    public void setArInt(BigDecimal arInt) {
        this.arInt = arInt;
    }

    public BigDecimal getNrMoney() {
        return nrMoney;
    }

    public void setNrMoney(BigDecimal nrMoney) {
        this.nrMoney = nrMoney;
    }

    public BigDecimal getOwMoney() {
        return owMoney;
    }

    public void setOwMoney(BigDecimal owMoney) {
        this.owMoney = owMoney;
    }

    public BigDecimal getOwInt() {
        return owInt;
    }

    public void setOwInt(BigDecimal owInt) {
        this.owInt = owInt;
    }

    public String getAchkDte() {
        return achkDte;
    }

    public void setAchkDte(String achkDte) {
        this.achkDte = achkDte;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public BigDecimal getEmrk() {
        return emrk;
    }

    public void setEmrk(BigDecimal emrk) {
        this.emrk = emrk;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getMneq() {
        return mneq;
    }

    public void setMneq(String mneq) {
        this.mneq = mneq;
    }

    public String getIdnLib() {
        return idnLib;
    }

    public void setIdnLib(String idnLib) {
        this.idnLib = idnLib;
    }

    public String getCitrmMrk() {
        return citrmMrk;
    }

    public void setCitrmMrk(String citrmMrk) {
        this.citrmMrk = citrmMrk;
    }

    public String getDup() {
        return dup;
    }

    public void setDup(String dup) {
        this.dup = dup;
    }

    public String getFiller05() {
        return filler05;
    }

    public void setFiller05(String filler05) {
        this.filler05 = filler05;
    }

}
