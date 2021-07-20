package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 審核給付清單紀錄檔
 * 
 * @author Rickychi
 */
@Table("BALP0D020")
public class Balp0d020 implements Serializable {
    @PkeyField("BALP0D020ID")
    private BigDecimal balp0d020Id;// 資料列編號
    private String payCode;// 給付別
    private String apNo;// 受理編號
    private String seqNo;// 受款人序號
    private String issuYm;// 核定年月
    private String veriSeq;// 編審序號(版次)
    private String caseTyp;// 案件類別
    private String mchkTyp;// 月核案件類別
    private String paysYm;// 給付年月(起)
    private String payeYm;// 給付年月(迄)
    private BigDecimal tissueAmt;// 合計核定金額
    private BigDecimal taplPayAmt;// 合計實付金額
    private String rptDate;// 列印/打包日期
    private BigDecimal pageNo;// 列印/打包頁次
    private String chkDate;// 審核日期
    private String chkMan;// 審核人員
    private String mexcLvl;// 應決行層級
    private String exeMk;// 複核/決行狀態
    private String evtIds;// 事故者社福識別碼
    private String evtIdnNo;// 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String evtName;// 事故者姓名
    private String benIds;// 受益人社福識別碼
    private String benIdnNo;// 受益人身分證號
    private String benBrDate;// 受益人出生日期
    private String benName;// 受益人姓名
    private String procMk;// 處理註記
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String acceptMk; // 合格註記
    private String manchkMk; // 人工審核結果
    private String realExcLvl;// 實際決行層級
    private String reChkDate;// 複核日期
    private String reChkMan;// 複核人員
    private String exeDate;// 決行日期/退件日期
    private String exeMan;// 決行人員/退件人員

    // Field not in BAAPPBASE
    // [
    private String empNo;// 員工編號
    private String procStat;// 處理狀態

    // ]

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
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

    public BigDecimal getBalp0d020Id() {
        return balp0d020Id;
    }

    public void setBalp0d020Id(BigDecimal balp0d020Id) {
        this.balp0d020Id = balp0d020Id;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
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

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getMchkTyp() {
        return mchkTyp;
    }

    public void setMchkTyp(String mchkTyp) {
        this.mchkTyp = mchkTyp;
    }

    public String getPaysYm() {
        return paysYm;
    }

    public void setPaysYm(String paysYm) {
        this.paysYm = paysYm;
    }

    public String getPayeYm() {
        return payeYm;
    }

    public void setPayeYm(String payeYm) {
        this.payeYm = payeYm;
    }

    public BigDecimal getTissueAmt() {
        return tissueAmt;
    }

    public void setTissueAmt(BigDecimal tissueAmt) {
        this.tissueAmt = tissueAmt;
    }

    public BigDecimal getTaplPayAmt() {
        return taplPayAmt;
    }

    public void setTaplPayAmt(BigDecimal taplPayAmt) {
        this.taplPayAmt = taplPayAmt;
    }

    public String getRptDate() {
        return rptDate;
    }

    public void setRptDate(String rptDate) {
        this.rptDate = rptDate;
    }

    public BigDecimal getPageNo() {
        return pageNo;
    }

    public void setPageNo(BigDecimal pageNo) {
        this.pageNo = pageNo;
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

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public String getExeMk() {
        return exeMk;
    }

    public void setExeMk(String exeMk) {
        this.exeMk = exeMk;
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

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
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

    public String getBenBrDate() {
        return benBrDate;
    }

    public void setBenBrDate(String benBrDate) {
        this.benBrDate = benBrDate;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getProcMk() {
        return procMk;
    }

    public void setProcMk(String procMk) {
        this.procMk = procMk;
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

    public String getRealExcLvl() {
        return realExcLvl;
    }

    public void setRealExcLvl(String realExcLvl) {
        this.realExcLvl = realExcLvl;
    }

    public String getReChkDate() {
        return reChkDate;
    }

    public void setReChkDate(String reChkDate) {
        this.reChkDate = reChkDate;
    }

    public String getReChkMan() {
        return reChkMan;
    }

    public void setReChkMan(String reChkMan) {
        this.reChkMan = reChkMan;
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
}
