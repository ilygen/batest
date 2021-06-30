package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞工紓困貸款繳納本息情形查詢單
 * 
 * @author Goston
 */
public class DataUpdateRpt01Case implements Serializable {
    // 給付主檔 (BAAPPBASE) - 事故者資料
    // [
    private String apNo; // 受理編號
    private String evtIdnNo; // 事故者身分證號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    // ]

    // 貸款主檔 (LNM)
    // [
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
    // ]

    // 月核定日期參數檔 (BAPAISSUDATE)
    // [
    private String issuDate; // 核定日期

    // ]

    /**
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    /**
     * 收件編號 (銀行別 + 分行代號 + 流水號)
     * 
     * @return
     */
    public String getRcvNoString() {
        StringBuffer receiveNo = new StringBuffer("");
        receiveNo.append(StringUtils.leftPad(StringUtils.defaultString(bankCd), 3, "0")); // 銀行別 3 碼
        receiveNo.append("-");
        receiveNo.append(StringUtils.leftPad(((brNo == null) ? "" : brNo), 4, "0")); // 分行代號 4 碼
        receiveNo.append("-");
        receiveNo.append(StringUtils.leftPad(((seqNo == null) ? "" : seqNo), 6, "0")); // 分行代號 4 碼

        return receiveNo.toString();
    }

    /**
     * 出生日期
     * 
     * @return
     */
    public String getBrDteAplyString() {
        if (StringUtils.length(brDteAply) == 8)
            return DateUtility.formatChineseDateString(brDteAply, true);
        else
            return StringUtils.defaultString(brDteAply);
    }

    /**
     * 撥款日期
     * 
     * @return
     */
    public String getDatePayString() {
        if (StringUtils.length(datePay) == 8)
            return DateUtility.formatChineseDateString(datePay, true);
        else
            return StringUtils.defaultString(datePay);
    }

    /**
     * 核定日期
     * 
     * @return
     */
    public String getIssuDateString() {
        if (StringUtils.length(issuDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(issuDate), true);
        else
            return StringUtils.defaultString(issuDate);
    }

    public DataUpdateRpt01Case() {

    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
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

    public String getIssuDate() {
        return issuDate;
    }

    public void setIssuDate(String issuDate) {
        this.issuDate = issuDate;
    }

}
