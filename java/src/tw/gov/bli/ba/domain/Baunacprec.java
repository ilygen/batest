package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 應收帳務記錄檔
 * 
 * @author Rickychi
 */
public class Baunacprec implements Serializable {
    private BigDecimal baunacprecId; // 資料列編號(應收帳務編號)
    private BigDecimal unacpRecNo; // 應收帳務序號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String recKind; // 收回種類
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String benIds; // 受款人社福識別碼
    private String benIdnNo; // 受款人身分證號
    private String benName; // 受款人姓名
    private String benBrDate; // 受款人出生日期
    private String unacpDesc; // 應收原因
    private String unacpDate; // 應收立帳日期
    private BigDecimal recAmt; // 應收金額
    private BigDecimal serCnt; // 分期期數
    private BigDecimal serAmt; // 每期金額
    private BigDecimal woAmt; // 已收金額
    private BigDecimal realRecAmt; // 實收金額
    private BigDecimal recRem; // 未收金額
    private BigDecimal intAmt; // 利息收入
    private String recDate; // 收回日期
    private String aplPayDate; // 入帳日期
    private String procMk; // 處理回覆結果
    private String procMkDate; // 處理回覆日期
    private String procStat; // 處理狀態
    private String procMan; // 處理人員
    private String procDate; // 處理日期
    private String evtIds; // 被保險人社福識別碼
    private String evtIdnNo; // 被保險人身分證號
    private String evtName; // 被保險人姓名
    private String evtBrDate; // 被保險人出生日期

    // Field not in BAUNACPDTL
    // [
    private String befBenName; // 更正前受款人姓名
    private String befBenIdnNo; // 更正前受款人身份證號
    private String befRecKind; // (更正前)收回方式
    private String befIssuYm; // (更正前)核定年月
    private String befPayYm; // (更正前)給付年月
    private BigDecimal befRecAmt; // (更正前)應收金額
    private BigDecimal befWoAmt; // (更正前)已收金額

    // ]

    public BigDecimal getBaunacprecId() {
        return baunacprecId;
    }

    public void setBaunacprecId(BigDecimal baunacprecId) {
        this.baunacprecId = baunacprecId;
    }

    public BigDecimal getUnacpRecNo() {
        return unacpRecNo;
    }

    public void setUnacpRecNo(BigDecimal unacpRecNo) {
        this.unacpRecNo = unacpRecNo;
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

    public String getRecKind() {
        return recKind;
    }

    public void setRecKind(String recKind) {
        this.recKind = recKind;
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

    public String getUnacpDesc() {
        return unacpDesc;
    }

    public void setUnacpDesc(String unacpDesc) {
        this.unacpDesc = unacpDesc;
    }

    public String getUnacpDate() {
        return unacpDate;
    }

    public void setUnacpDate(String unacpDate) {
        this.unacpDate = unacpDate;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getSerCnt() {
        return serCnt;
    }

    public void setSerCnt(BigDecimal serCnt) {
        this.serCnt = serCnt;
    }

    public BigDecimal getSerAmt() {
        return serAmt;
    }

    public void setSerAmt(BigDecimal serAmt) {
        this.serAmt = serAmt;
    }

    public BigDecimal getWoAmt() {
        return woAmt;
    }

    public void setWoAmt(BigDecimal woAmt) {
        this.woAmt = woAmt;
    }

    public BigDecimal getRealRecAmt() {
        return realRecAmt;
    }

    public void setRealRecAmt(BigDecimal realRecAmt) {
        this.realRecAmt = realRecAmt;
    }

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    public String getRecDate() {
        return recDate;
    }

    public void setRecDate(String recDate) {
        this.recDate = recDate;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
    }

    public String getProcMk() {
        return procMk;
    }

    public void setProcMk(String procMk) {
        this.procMk = procMk;
    }

    public String getProcMkDate() {
        return procMkDate;
    }

    public void setProcMkDate(String procMkDate) {
        this.procMkDate = procMkDate;
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

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
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

    public String getBefBenName() {
        return befBenName;
    }

    public void setBefBenName(String befBenName) {
        this.befBenName = befBenName;
    }

    public String getBefBenIdnNo() {
        return befBenIdnNo;
    }

    public void setBefBenIdnNo(String befBenIdnNo) {
        this.befBenIdnNo = befBenIdnNo;
    }

    public String getBefRecKind() {
        return befRecKind;
    }

    public void setBefRecKind(String befRecKind) {
        this.befRecKind = befRecKind;
    }

    public String getBefIssuYm() {
        return befIssuYm;
    }

    public void setBefIssuYm(String befIssuYm) {
        this.befIssuYm = befIssuYm;
    }

    public String getBefPayYm() {
        return befPayYm;
    }

    public void setBefPayYm(String befPayYm) {
        this.befPayYm = befPayYm;
    }

    public BigDecimal getBefRecAmt() {
        return befRecAmt;
    }

    public void setBefRecAmt(BigDecimal befRecAmt) {
        this.befRecAmt = befRecAmt;
    }

    public BigDecimal getBefWoAmt() {
        return befWoAmt;
    }

    public void setBefWoAmt(BigDecimal befWoAmt) {
        this.befWoAmt = befWoAmt;
    }
}
