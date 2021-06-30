package tw.gov.bli.ba.reviewfee.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 複檢費用審核作業
 * 
 * @author Goston
 */
public class ReviewFeeReviewDetailCase implements Serializable {
    // BABCML7
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
    private String accName; // 戶名

    // Other
    private String reasName; // 複檢原因 - 中文
    private String hosName; // 醫療院所 - 中文
    private String benNationName; // 受款人國籍名稱
    private String benEvtRelName; // 受益人與事故者關係 - 中文
    private String payTypName; // 給付方式 - 中文

    /**
     * 通知複檢日期
     * 
     * @return
     */
    public String getInreDateString() {
        return DateUtility.formatChineseDateString(inreDate, false);
    }

    /**
     * 複檢原因
     * 
     * @return
     */
    public String getReasString() {
        StringBuffer str = new StringBuffer("");

        if (StringUtils.isNotBlank(reasCode))
            str.append("-" + StringUtils.defaultString(reasCode));
        if (StringUtils.isNotBlank(reasName))
            str.append("-" + StringUtils.defaultString(reasName));

        if (StringUtils.isNotBlank(str.toString()))
            return StringUtils.substring(str.toString(), 1, str.toString().length()); // 去掉開頭的 Dash 符號
        else
            return str.toString();
    }

    /**
     * 醫療院所代碼
     * 
     * @return
     */
    public String getHosString() {
        StringBuffer str = new StringBuffer("");

        str.append(StringUtils.defaultString(hosId));
        if (StringUtils.isNotBlank(hosName))
            str.append(" (" + StringUtils.defaultString(hosName) + ")");

        return str.toString();
    }

    /**
     * 複檢費用申請日期
     * 
     * @return
     */
    public String getRecosDateString() {
        return DateUtility.formatChineseDateString(recosDate, false);
    }

    /**
     * 受益人與事故者關係
     * 
     * @return
     */
    public String getBenEvtRelString() {
        StringBuffer str = new StringBuffer("");

        if (StringUtils.isNotBlank(benEvtRel))
            str.append("-" + StringUtils.defaultString(benEvtRel));
        if (StringUtils.isNotBlank(benEvtRelName))
            str.append("-" + StringUtils.defaultString(benEvtRelName));

        if (StringUtils.isNotBlank(str.toString()))
            return StringUtils.substring(str.toString(), 1, str.toString().length()); // 去掉開頭的 Dash 符號
        else
            return str.toString();
    }

    /**
     * 受益人出生日期
     * 
     * @return
     */
    public String getBenBrDateString() {
        return DateUtility.formatChineseDateString(benBrDate, false);
    }

    /**
     * 受益人國籍別
     * 
     * @return
     */
    public String getBenNationTypString() {
        if (StringUtils.equals(benNationTyp, "1"))
            return "本國";
        else
            return "外籍";
    }

    /**
     * 受益人性別
     * 
     * @return
     */
    public String getBenSexString() {
        if (StringUtils.equals(benSex, "1"))
            return "男";
        else
            return "女";
    }

    /**
     * 受益人國籍
     * 
     * @return
     */
    public String getBenNationString() {
        if (StringUtils.isNotBlank(benNationName))
            return benNationName;
        else
            return benNationCode;
    }

    /**
     * 給付方式
     * 
     * @return
     */
    public String getPayTypString() {
        StringBuffer str = new StringBuffer("");

        if (StringUtils.isNotBlank(payTyp))
            str.append("-" + StringUtils.defaultString(payTyp));
        if (StringUtils.isNotBlank(payTypName))
            str.append("-" + StringUtils.defaultString(payTypName));

        if (StringUtils.isNotBlank(str.toString()))
            return StringUtils.substring(str.toString(), 1, str.toString().length()); // 去掉開頭的 Dash 符號
        else
            return str.toString();
    }

    /**
     * 帳號
     * 
     * @return
     */
    public String getAccountNoString() {
        if (StringUtils.equals(payTyp, "1") || StringUtils.equals(payTyp, "2"))
            return StringUtils.defaultString(payBankId) + StringUtils.defaultString(branchId) + "-" + StringUtils.defaultString(payeeAcc);
        else if (StringUtils.equals(payTyp, "5") || StringUtils.equals(payTyp, "6"))
            return StringUtils.defaultString(payBankId) + StringUtils.defaultString(branchId) + StringUtils.defaultString(payeeAcc);
        else
            return "";
    }

    /**
     * 通訊地址別
     * 
     * @return
     */
    public String getCommTypString() {
        if (StringUtils.equals(commTyp, "1"))
            return "同戶籍地";
        else
            return "現住址";
    }

    public ReviewFeeReviewDetailCase() {

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

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
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

    public String getBenNationName() {
        return benNationName;
    }

    public void setBenNationName(String benNationName) {
        this.benNationName = benNationName;
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
