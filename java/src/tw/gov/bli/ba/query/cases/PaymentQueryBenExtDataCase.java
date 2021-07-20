package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 給付查詢作業 受款人核定資料/編審住記資料
 * 
 * @author Rickychi
 */
public class PaymentQueryBenExtDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    // 核定資料欄位
    // [
    private String baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String benIdnNo; // 受款人身分證號
    private String benName; // 受款人姓名
    private String benBrDate; // 受款人出生日期
    private String benEvtRel; // 關係
    private String chgNote; // 更正原因
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String acceptMk; // 電腦編審結果
    private String manchkMk; // 人工審核結果
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal adjustAmt;// 調整金額
    private BigDecimal recAmt;// 應收金額
    private BigDecimal supAmt;// 補發金額
    private BigDecimal otherAmt;// 事故者扣減總金額
    private BigDecimal payRate;// 匯款匯費
    private BigDecimal aplPayAmt;// 實付金額
    // ]

    // 編審註記資料
    // [
    private String issuPayYm;// 編審核定年月
    private Integer chkFileDataSize;// 資料筆數
    private List<PaymentQueryChkFileDataCase> benChkDataList;// 編審註記資料
    // ]

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getBenBrDateStr() {
        if (StringUtils.isNotBlank(getBenBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getBenBrDate());
        }
        else {
            return "";
        }
    }

    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm()) && getIssuYm().length() == 6) {
            return DateUtility.changeDateType(getIssuYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm()) && getPayYm().length() == 6) {
            return DateUtility.changeDateType(getPayYm() + "01").substring(0, 5);
        }
        else {
            return "";
        }
    }

    // ]

    public String getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(String baappbaseId) {
        this.baappbaseId = baappbaseId;
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

    public String getChgNote() {
        return chgNote;
    }

    public void setChgNote(String chgNote) {
        this.chgNote = chgNote;
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

    public String getAcceptMk() {
        return acceptMk;
    }

    public void setAcceptMk(String acceptMk) {
        this.acceptMk = acceptMk;
    }

    public String getManchkMk() {
        return manchkMk;
    }

    public void setManchkMk(String manchkMk) {
        this.manchkMk = manchkMk;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(BigDecimal adjustAmt) {
        this.adjustAmt = adjustAmt;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSupAmt() {
        return supAmt;
    }

    public void setSupAmt(BigDecimal supAmt) {
        this.supAmt = supAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
    }

    public String getIssuPayYm() {
        return issuPayYm;
    }

    public void setIssuPayYm(String issuPayYm) {
        this.issuPayYm = issuPayYm;
    }

    public Integer getChkFileDataSize() {
        return chkFileDataSize;
    }

    public void setChkFileDataSize(Integer chkFileDataSize) {
        this.chkFileDataSize = chkFileDataSize;
    }

    public List<PaymentQueryChkFileDataCase> getBenChkDataList() {
        return benChkDataList;
    }

    public void setBenChkDataList(List<PaymentQueryChkFileDataCase> benChkDataList) {
        this.benChkDataList = benChkDataList;
    }
}
