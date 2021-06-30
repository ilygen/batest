package tw.gov.bli.ba.domain;

import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 另案扣減紀錄檔
 * 
 * @author Rickychi
 */
@Table("BACUTREC")
public class Bacutrec {
    @PkeyField("BACUTRECID")
    private BigDecimal bacutrecId;// 資料列編號

    private BigDecimal baappbaseId;// 給付主檔資料編號
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private BigDecimal cutRecSeqNo;// 扣減序號
    private String disPayKind;// 扣減給付種類
    private String typeMk;// 類別註記
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private String benIds;// 被保險人社福識別碼
    private String benIdnNo;// 被保險人身分證號
    private String benName;// 被保險人姓名
    private String benBrDate;// 被保險人出生日期
    private String recId;// 應收記錄編號
    private BigDecimal disAmt;// 扣減金額
    private String disDate;// 扣減日期
    private BigDecimal recAmt;// 收回金額
    private String recDate;// 收回日期
    private String procStat;// 處理狀態
    private String procMan;// 處理人員
    private String procDate;// 處理日期

    public BigDecimal getBacutrecId() {
        return bacutrecId;
    }

    public void setBacutrecId(BigDecimal bacutrecId) {
        this.bacutrecId = bacutrecId;
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

    public BigDecimal getCutRecSeqNo() {
        return cutRecSeqNo;
    }

    public void setCutRecSeqNo(BigDecimal cutRecSeqNo) {
        this.cutRecSeqNo = cutRecSeqNo;
    }

    public String getDisPayKind() {
        return disPayKind;
    }

    public void setDisPayKind(String disPayKind) {
        this.disPayKind = disPayKind;
    }

    public String getTypeMk() {
        return typeMk;
    }

    public void setTypeMk(String typeMk) {
        this.typeMk = typeMk;
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

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
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

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public BigDecimal getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(BigDecimal disAmt) {
        this.disAmt = disAmt;
    }

    public String getDisDate() {
        return disDate;
    }

    public void setDisDate(String disDate) {
        this.disDate = disDate;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getProcMan() {
        return procMan;
    }

    public void setProcMan(String procMan) {
        this.procMan = procMan;
    }

    public String getProcDate() {
        return procDate;
    }

    public void setProcDate(String procDate) {
        this.procDate = procDate;
    }

}
