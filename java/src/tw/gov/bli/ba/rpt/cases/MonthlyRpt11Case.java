package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 退匯核定清單
 * 
 * @author Rickychi
 */
public class MonthlyRpt11Case implements Serializable {
    private static final long serialVersionUID = -6541879602291890822L;
    private BigDecimal apNoAmt;// 件數
    private BigDecimal dataAmt;// 筆數
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal otheraAmt;// 另案扣減(同類保險)金額
    private BigDecimal otherbAmt;// 另案扣減(他類保險)金額
    private BigDecimal mitRate;// 匯款匯費
    private BigDecimal offsetAmt;// 抵銷紓困
    private BigDecimal compenAmt;// 代扣補償金
    private BigDecimal inherItorAmt;// 保留遺屬津貼
    private BigDecimal itrtTax;// 代扣利息所得稅
    private BigDecimal otherAmt;// 其他費用
    private String issuTyp;// 案件類別
    private String payTyp;// 給付方式
    private String chkDate;// 核定日期

    // Footer使用
    private String rptTyp;
    private String payCode;
    private String issuYm;
    // private String payTyp;
    // private String chkDate;
    private String benEvtRel;
    private BigDecimal accountSeq;
    private BigDecimal seqNo;
    private String accountNo;
    private String accountName;
    private BigDecimal payAmt;
    private String paySeqNo;
    private String procTime;
    private BigDecimal rptPage; // 清冊頁次
    private BigDecimal eRptPage; // 清冊頁次
    private String cPrnDate; // 印表日期

    // 判斷給付別使用
    private String nlWkMk;
    private String adWkMk;
    private String naChgMk;
    private String isNaChgMk;

    /**
     * 印表日期
     * 
     * @return
     */
    public String getcPrnDateStr() {
        if (StringUtils.isNotBlank(getcPrnDate()))
            return DateUtility.changeDateType(getcPrnDate());
        else
            return "";
    }

    // 頁面顯示轉換
    // [
    public String getPayTypStr() {
        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(getPayTyp()))
            return "金融轉帳";// 原應為"匯入銀行帳號",此報表改為"金融轉帳"
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_4).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_6).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_7).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_7;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_8).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_8;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_9).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_9;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_A).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_A;
        else if (("").equals(getPayTyp()))
            return "其它";
        else
            return "";
    }

    public String getMchkTypStr() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else
            return "";
    }

    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate()))
            return DateUtility.changeDateType(getChkDate());
        else
            return "";
    }

    // ]

    public BigDecimal getApNoAmt() {
        return apNoAmt;
    }

    public void setApNoAmt(BigDecimal apNoAmt) {
        this.apNoAmt = apNoAmt;
    }

    public BigDecimal getDataAmt() {
        return dataAmt;
    }

    public void setDataAmt(BigDecimal dataAmt) {
        this.dataAmt = dataAmt;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getOtheraAmt() {
        return otheraAmt;
    }

    public void setOtheraAmt(BigDecimal otheraAmt) {
        this.otheraAmt = otheraAmt;
    }

    public BigDecimal getOtherbAmt() {
        return otherbAmt;
    }

    public void setOtherbAmt(BigDecimal otherbAmt) {
        this.otherbAmt = otherbAmt;
    }

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getCompenAmt() {
        return compenAmt;
    }

    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }

    public BigDecimal getInherItorAmt() {
        return inherItorAmt;
    }

    public void setInherItorAmt(BigDecimal inherItorAmt) {
        this.inherItorAmt = inherItorAmt;
    }

    public BigDecimal getItrtTax() {
        return itrtTax;
    }

    public void setItrtTax(BigDecimal itrtTax) {
        this.itrtTax = itrtTax;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
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

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    // Footer使用

    public String getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public BigDecimal getAccountSeq() {
        return accountSeq;
    }

    public void setAccountSeq(BigDecimal accountSeq) {
        this.accountSeq = accountSeq;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getPaySeqNo() {
        return paySeqNo;
    }

    public void setPaySeqNo(String paySeqNo) {
        this.paySeqNo = paySeqNo;
    }

    public String getNlWkMk() {
        return nlWkMk;
    }

    public void setNlWkMk(String nlWkMk) {
        this.nlWkMk = nlWkMk;
    }

    public String getAdWkMk() {
        return adWkMk;
    }

    public void setAdWkMk(String adWkMk) {
        this.adWkMk = adWkMk;
    }

    public String getNaChgMk() {
        return naChgMk;
    }

    public void setNaChgMk(String naChgMk) {
        this.naChgMk = naChgMk;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }

    public BigDecimal getRptPage() {
        return rptPage;
    }

    public void setRptPage(BigDecimal rptPage) {
        this.rptPage = rptPage;
    }

    public BigDecimal geteRptPage() {
        return eRptPage;
    }

    public void seteRptPage(BigDecimal eRptPage) {
        this.eRptPage = eRptPage;
    }

    public String getcPrnDate() {
        return cPrnDate;
    }

    public void setcPrnDate(String cPrnDate) {
        this.cPrnDate = cPrnDate;
    }

    public String getIsNaChgMk() {
        return isNaChgMk;
    }

    public void setIsNaChgMk(String isNaChgMk) {
        this.isNaChgMk = isNaChgMk;
    }

}
