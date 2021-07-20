package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 給付編審檔
 * 
 * @author Rickychi
 */
@Table("BACHKFILE")
public class Bachkfile implements Serializable {
    @PkeyField("BACHKFILEID")@LogField("BACHKFILEID")
    private BigDecimal baChkFileId; // 資料列編號
    @PkeyField("BAAPPBASEID")@LogField("BAAPPBASEID")
    private BigDecimal baappbaseId; // 給付主檔資料編號
    @PkeyField("APNO")@LogField("APNO")
    private String apNo; // 受理編號
    @PkeyField("SEQNO")@LogField("SEQNO")
    private String seqNo; // 序號
    @PkeyField("ISSUYM")@LogField("ISSUYM")
    private String issuYm; // 核定年月
    @PkeyField("PAYYM")@LogField("PAYYM")
    private String payYm; // 給付年月
    @PkeyField("CHKCODE")@LogField("CHKCODE")
    private String chkCode; // 編審註記代號
    @PkeyField("CHKTYP")@LogField("CHKTYP")
    private String chkTyp; // 編審註記種類
    @LogField("CHKCODEPRE")
    private String chkCodePre; // 編審註記代號類型
    @LogField("CHKCODEPOST")
    private String chkCodePost; // 編審註記代號改後類型
    @LogField("CHKRESULT")
    private String chkResult; // 編審結果說明
    @LogField("KEYVALUE")
    private String keyValue; // 關鍵欄位值

    // Field not in BACHKFILE
    // [
    private String benIdnNo; // 編審註記程度調整-受益人身分證號
    private String benName; // 編審註記程度調整-受益人姓名
    private String benBrDate; // 編審註記程度調整-受益人出生日期
    private String benEvtRel; // 編審註記程度調整-受益人與事故者關係

    private String maxMk; // 最大給付年月註記 for 失能年金編審註記程度調整
    private String valisYm; // 有效年月起 for 失能年金編審註記程度調整
    private String valieYm; // 有效年月迄 for 失能年金編審註記程度調整

    private String chkFileName; // 編審註記代號 + 編審註記代號改後類型 for 失能年金案件資料更正
    private String chkFileDesc; // 編審註記代號 + 編審結果說明 for 失能年金案件資料更正
    private String chkFileCode; // 編審註記代號 for 失能年金案件資料更正
    private String evtJobDate;//事故者離職日期
    private String procStat;//處理狀態
    private String caseTyp;//資料別
    // ]

    // 頁面顯示轉換
    // [
    public String getIssuYmStr() {
        if (StringUtils.isNotBlank(getIssuYm())) {
            return DateUtility.changeWestYearMonthType(getIssuYm());
        }
        else {
            return getIssuYm();
        }
    }

    public String getPayYmStr() {
        if (StringUtils.isNotBlank(getPayYm())) {
            return DateUtility.changeWestYearMonthType(getPayYm());
        }
        else {
            return getPayYm();
        }
    }

    // ]

    public BigDecimal getBaChkFileId() {
        return baChkFileId;
    }

    public void setBaChkFileId(BigDecimal baChkFileId) {
        this.baChkFileId = baChkFileId;
    }

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
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

    public String getChkTyp() {
        return chkTyp;
    }

    public void setChkTyp(String chkTyp) {
        this.chkTyp = chkTyp;
    }

    public String getChkCode() {
        return chkCode;
    }
    
    public String getChkCodeReplaceEscap() {
        return StringUtils.replace(chkCode, "\\", "slash");
    }

    public void setChkCode(String chkCode) {
        this.chkCode = chkCode;
    }

    public String getChkCodePre() {
        return chkCodePre;
    }

    public void setChkCodePre(String chkCodePre) {
        this.chkCodePre = chkCodePre;
    }

    public String getChkCodePost() {
        return chkCodePost;
    }

    public void setChkCodePost(String chkCodePost) {
        this.chkCodePost = chkCodePost;
    }

    public String getChkResult() {
        return chkResult;
    }

    public void setChkResult(String chkResult) {
        this.chkResult = chkResult;
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

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getMaxMk() {
        return maxMk;
    }

    public void setMaxMk(String maxMk) {
        this.maxMk = maxMk;
    }

    public String getValisYm() {
        return valisYm;
    }

    public void setValisYm(String valisYm) {
        this.valisYm = valisYm;
    }

    public String getValieYm() {
        return valieYm;
    }

    public void setValieYm(String valieYm) {
        this.valieYm = valieYm;
    }

    public String getChkFileName() {
        return chkFileName;
    }

    public void setChkFileName(String chkFileName) {
        this.chkFileName = chkFileName;
    }

    public String getChkFileDesc() {
        return chkFileDesc;
    }

    public void setChkFileDesc(String chkFileDesc) {
        this.chkFileDesc = chkFileDesc;
    }

    public String getChkFileCode() {
        return chkFileCode;
    }

    public void setChkFileCode(String chkFileCode) {
        this.chkFileCode = chkFileCode;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }
    
    

}
