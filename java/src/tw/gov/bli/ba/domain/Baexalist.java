package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;

/**
 * 審核決行清單紀錄檔
 * 
 * @author Rickychi
 */
@Table("BAEXALIST")
public class Baexalist implements Serializable {
    @PkeyField("RPTTYP")
    private String rptTyp;// 報表種類

    @PkeyField("RPTDATE")
    private String rptDate;// 列印日期

    @PkeyField("PAGENO")
    private BigDecimal pageNo;// 頁次

    @PkeyField("SEQNO")
    private BigDecimal seqNo;// 序號

    private String payTyp;// 給付別
    private String apNo;// 受理編號
    private String evtName;// 事故者姓名
    private String paysYm; // 給付年月(起)
    private String payeYm; // 給付年月(迄)
    private BigDecimal tissueAmt;// 合計核定金額
    private BigDecimal taplPayAmt;// 合計實付金額
    private String chkMan;// 審核人員
    private String upCauseCode; // 異動狀況
    private String cprnDate; // 核定清單印表日期
    private String cprnPage; // 核定清單頁次
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String mtestMk;// 處理註記
    private String caseTyp;// 案件類別(資料別)
    private String issuYm;// 核定年月
    private String veriSeq;// 編審序號
    private String chkDate;// 審核日期
    private String mexcLvl;// 應決行層級
    private String chkList;// 本次編審註記列表
    private String preChkList;// 前次編審註記列表

    // Field not in BAEXALIST
    // [
    private BigDecimal baappbaseId;// 資料列編號
    private String evtIdnNo;// 事故者身分證號
    private BigDecimal lsInsmAmt; // 投保金額
    private String evtJobDate;// 事故者離職日期
    private String evtDate;// 事故日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期
    private String evtDieDate;// 事故者死亡日期
    private String isShowCond1;// 審核管制條件1 N:不顯示; Y:顯示「最後單位不得為空」
    private String isShowCond2;// 審核管制條件2 N:不顯示; Y:顯示「實發年資有誤」
    private String isShowCond3;// 審核管制條件3 N:不顯示; Y:顯示「單位墊付金額不得為零」
    private String isShowCond4;// 審核管制條件4 N:不顯示; Y:顯示「實際補償金額不得為零」
    private String isShowCond5;// 審核管制條件5 N:不顯示; Y:顯示「」
    private String isShowCond6;// 審核管制條件6 N:不顯示; Y:顯示「受款人可領金額有誤」
    private String isShowCond7;// 審核管制條件7 N:不顯示; Y:顯示「通訊地址欄位不得為空白」
    private String isShowCond8;// 審核管制條件8 N:不顯示; Y:顯示「編審註記待處理」
    private String isShowCond9;// 審核管制條件9 N:不顯示; Y:顯示「行政支援記錄未銷案」
    private String maxPayYm; // 給付年月起
    private String minPayYm; // 給付年月迄
    private BigDecimal aplPayAmt; // 實付總額
    private BigDecimal issueAmt;// 核定金額
    private String procStat;// 處理狀態
    private String manchkMk;// 人工審核結果
    private String reChk;// 複核是否顯示條件
    private String exeMk; // 決行是否顯示條件

    private String accptMk; // 電腦審核結果
    private String evtIds; // 事故者社福識別碼
    private String evtBrDate; // 事故者出生日期
    private String benIds; // 受益人社福識別碼
    private String benIdnNo; // 受益人身分證號
    private String benBrDate; // 受益人出生日期
    private String benName; // 受益人姓名
    private String mchkTyp; // 月核案件類別
    private String flag1; // 審核FLAG

    // ]

    public BigDecimal getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(BigDecimal baappbaseId) {
        this.baappbaseId = baappbaseId;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public BigDecimal getLsInsmAmt() {
        return lsInsmAmt;
    }

    public void setLsInsmAmt(BigDecimal lsInsmAmt) {
        this.lsInsmAmt = lsInsmAmt;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getEvtEligibleDate() {
        return evtEligibleDate;
    }

    public void setEvtEligibleDate(String evtEligibleDate) {
        this.evtEligibleDate = evtEligibleDate;
    }

    public String getEvtMissingDate() {
        return evtMissingDate;
    }

    public void setEvtMissingDate(String evtMissingDate) {
        this.evtMissingDate = evtMissingDate;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getIsShowCond1() {
        return isShowCond1;
    }

    public void setIsShowCond1(String isShowCond1) {
        this.isShowCond1 = isShowCond1;
    }

    public String getIsShowCond2() {
        return isShowCond2;
    }

    public void setIsShowCond2(String isShowCond2) {
        this.isShowCond2 = isShowCond2;
    }

    public String getIsShowCond3() {
        return isShowCond3;
    }

    public void setIsShowCond3(String isShowCond3) {
        this.isShowCond3 = isShowCond3;
    }

    public String getIsShowCond4() {
        return isShowCond4;
    }

    public void setIsShowCond4(String isShowCond4) {
        this.isShowCond4 = isShowCond4;
    }

    public String getIsShowCond5() {
        return isShowCond5;
    }

    public void setIsShowCond5(String isShowCond5) {
        this.isShowCond5 = isShowCond5;
    }

    public String getIsShowCond6() {
        return isShowCond6;
    }

    public void setIsShowCond6(String isShowCond6) {
        this.isShowCond6 = isShowCond6;
    }

    public String getIsShowCond7() {
        return isShowCond7;
    }

    public void setIsShowCond7(String isShowCond7) {
        this.isShowCond7 = isShowCond7;
    }

    public String getMaxPayYm() {
        return maxPayYm;
    }

    public void setMaxPayYm(String maxPayYm) {
        this.maxPayYm = maxPayYm;
    }

    public String getMinPayYm() {
        return minPayYm;
    }

    public void setMinPayYm(String minPayYm) {
        this.minPayYm = minPayYm;
    }

    public BigDecimal getAplPayAmt() {
        return aplPayAmt;
    }

    public void setAplPayAmt(BigDecimal aplPayAmt) {
        this.aplPayAmt = aplPayAmt;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
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

    public BigDecimal getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
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

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getIsShowCond8() {
        return isShowCond8;
    }

    public void setIsShowCond8(String isShowCond8) {
        this.isShowCond8 = isShowCond8;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getManchkMk() {
        return manchkMk;
    }

    public void setManchkMk(String manchkMk) {
        this.manchkMk = manchkMk;
    }

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public String getReChk() {
        return reChk;
    }

    public void setReChk(String reChk) {
        this.reChk = reChk;
    }

    public String getExeMk() {
        return exeMk;
    }

    public void setExeMk(String exeMk) {
        this.exeMk = exeMk;
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

    public String getUpCauseCode() {
        return upCauseCode;
    }

    public void setUpCauseCode(String upCauseCode) {
        this.upCauseCode = upCauseCode;
    }

    public String getCprnDate() {
        return cprnDate;
    }

    public void setCprnDate(String cprnDate) {
        this.cprnDate = cprnDate;
    }

    public String getCprnPage() {
        return cprnPage;
    }

    public void setCprnPage(String cprnPage) {
        this.cprnPage = cprnPage;
    }

    public String getIsShowCond9() {
        return isShowCond9;
    }

    public void setIsShowCond9(String isShowCond9) {
        this.isShowCond9 = isShowCond9;
    }

    public String getMtestMk() {
        return mtestMk;
    }

    public void setMtestMk(String mtestMk) {
        this.mtestMk = mtestMk;
    }

    public String getAccptMk() {
        return accptMk;
    }

    public void setAccptMk(String accptMk) {
        this.accptMk = accptMk;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
    }

    public String getEvtBrDate() {
        return evtBrDate;
    }

    public void setEvtBrDate(String evtBrDate) {
        this.evtBrDate = evtBrDate;
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

    public String getMchkTyp() {
        return mchkTyp;
    }

    public void setMchkTyp(String mchkTyp) {
        this.mchkTyp = mchkTyp;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getChkList() {
        return chkList;
    }

    public void setChkList(String chkList) {
        this.chkList = chkList;
    }

    public String getPreChkList() {
        return preChkList;
    }

    public void setPreChkList(String preChkList) {
        this.preChkList = preChkList;
    }
}
