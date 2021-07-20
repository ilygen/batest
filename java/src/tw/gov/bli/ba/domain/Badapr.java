package tw.gov.bli.ba.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import tw.gov.bli.common.annotation.LogField;
import tw.gov.bli.common.annotation.PkeyField;
import tw.gov.bli.common.annotation.Table;
import tw.gov.bli.common.util.DateUtil;

/**
 * 給付核定檔 (<code>BADAPR</code>)
 * 
 * @author swim
 */
@Table("BADAPR")
public class Badapr implements Serializable {
    private BigDecimal badaprId; // 資料列編號

    @PkeyField("BAAPPBASEID")
    private BigDecimal baappbaseId; // 給付主檔資料編號

    @PkeyField("APNO")
    private String apNo; // 受理編號

    private String seqNo; // 序號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String mtestMk; // 處理註記
    private String veriSeq; // 編審序號
    private String mchkTyp; // 月核類別
    private String mapNo; // 相關受理編號
    private String maprootMk; // 相關受理編號註記
    private String benIds; // 受益人社福識別碼
    private String benIdn; // 受益人身分證號
    private BigDecimal oldSeni; // 投保年資(日)
    private String oldab; // 第一式/第二式
    private BigDecimal oldaAmt; // 第一式式金額
    private BigDecimal oldbAmt; // 第二式式金額
    private String suprecMk; // 補發收回註記
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal adjustAmt; // 調整金額
    private BigDecimal issuCalcAmt; // 核定計算金額
    private BigDecimal recAmt; // 收回金額
    private BigDecimal supAmt; // 補發金額
    private BigDecimal aplpayAmt; // 實付金額
    private BigDecimal payRate; // 匯款匯費
    private BigDecimal annuAmt; // 累計已領年金金額
    private BigDecimal unitpayAmt; // 投保單位墊付金額
    private BigDecimal returnAmt; // 已歸墊金額
    private BigDecimal remainAmt; // 尚未歸墊金額
    private BigDecimal compenAmt; // 實際補償金額
    private BigDecimal lecomAmt; // 已代扣補償金額
    private BigDecimal recomAmt; // 未扣補償金金額
    private BigDecimal loanAmt; // 勞貸貸款金額
    private BigDecimal recapAmt; // 本次勞貸本金餘額
    private BigDecimal loaniTrt; // 本次勞貸利息
    private String dlineDate; // 本次勞貸本息截止日
    private BigDecimal offsetExp; // 本次抵銷勞貸其他費用
    private BigDecimal offsetAmt; // 本次抵銷金額
    private BigDecimal evtOtherAmt; // 事故者扣減金額
    private BigDecimal otherAmt; // 另案扣減金額
    private BigDecimal remitAmt; // 退匯沖抵金額
    private BigDecimal ovrcutAmt; // 退回現金沖抵
    private BigDecimal payBanance; // 給付沖抵
    private String aplpayMk; // 帳務註記
    private String aplpayDate; // 帳務日期
    private String genmedDate; // 產出媒體日期
    private String remitMk; // 後續註記
    private String remitDate; // 後續註記日期
    private String transActiond; // 退改匯交易編號
    private String stexpndMk; // 止付註記
    private String stexpndReason; // 止付原因
    private String stexpndDate; // 止付日期
    private String acceptMk; // 合格註記
    private String manchkMk; // 人工審核結果
    private String cprnDate; // 核定清單印表日期
    private BigDecimal cprnPage; // 核定清單頁次
    private String chkDate; // 核定日期
    private String procTime; // 處理日期時間
    private String bureauAcc; // 局帳號
    private String payEeacc; // 銀行帳號
    private BigDecimal insAvgAmt; // 平均薪資
    private BigDecimal itrmY; // 投保年資(年)
    private BigDecimal itrmD; // 投保年資(日)
    private BigDecimal nitrmY; // 投保年資(年-年金制)
    private BigDecimal nitrmM; // 投保年資(月-年金制)
    private BigDecimal noldtY; // 老年年資(年)
    private BigDecimal noldtM; // 老年年資(月)
    private BigDecimal valseniY; // 國保已繳年資(年)
    private BigDecimal valseniM; // 國保已繳年資(月)
    private BigDecimal aplPaySeniY; // 實付年資(年)
    private BigDecimal aplPaySeniM; // 實付年資(月)
    private BigDecimal oldRate; // 展延/減額比率
    private String oldRateSdate; // 展延/減額期間(起)
    private String oldRateEdate; // 展延/減額期間(迄)
    private BigDecimal evtOffsetAmt; // 攤紓困金額
    private BigDecimal badDebtAmt; // 呆帳金額
    private BigDecimal befIssueAmt; // 核定金額
    private BigDecimal otheraAmt; // 另案扣減(同類保險)金額
    private BigDecimal otherbAmt; // 另案扣減(他類保險)金額
    private BigDecimal ocAccaddAmt; // 已領職災增給金額
    private String payBankId; // 入帳銀行 (總行)
    private String branchId; // 入帳銀行 (分行)
    private BigDecimal oldExtraRate; // 遺屬展延/減額比率
    private BigDecimal cpiRate; // 核定計算金額物價調整指數
    private BigDecimal inheritorAmt; // 計息存儲金額

    // for OldAge Death Repay
    private String oriIssuYm;// 核定年月
    private String heirSeqNo;// 繼承人序
    private BigDecimal avgNum;// 繼承人分配後所得金額

    // Field not in BADAPR
    // [
    private String benName;// 受款人姓名
    private String benEvtRel;// 關係
    // payeeDataUpdate
    private String apItem; // 申請項目

    // StopPaymentProcess
    private String stexpnd; // 止付條件(註)
    private String evtJobDate; // 事故者離職日

    // DecisionRpt 01 begin ... [
    private String payYmBegin; // 給付年月 - 起
    private String payYmEnd; // 給付年月 - 迄
    // ] ... end

    private BigDecimal recAmtEvt;
    private BigDecimal recAmtBen;
    private BigDecimal otherAmtEvt;
    private BigDecimal otherAmtBen;

    // for MonthlyRpt04
    // [
    private BigDecimal badaprIdAmt;// 件數
    private BigDecimal amt;// 筆數
    // private BigDecimal befIssueAmt;// 核定金額
    private BigDecimal otherAamt;// 另案扣減(同類保險)金額
    private BigDecimal otherBamt;// 另案扣減(他類保險)金額
    // private BigDecimal payRate;// 匯款匯費
    // private BigDecimal offsetAmt;// 抵銷紓困
    // private BigDecimal compenAmt;// 代扣補償金
    private BigDecimal pay1;// 保留遺屬津貼
    private BigDecimal pay2;// 代扣利息所得稅
    private BigDecimal pay3;// 其他
    private String payTyp;// 給付方式
    private String payKind;
    private String issuYmStr;// 核定年月(民國年)
    private String payYmStr;// 給付年月(民國年)
    private BigDecimal oldAmt; // 老年-第一式式金額/第二式式金額(擇領)
    private BigDecimal qualCount; // 符合人數
    // ]

    private String prType;// 先核普通
    private String evTyp;// 傷病分類
    private String evtIdnNo;// 事故者身分證號
    private String issuPayYm;// 核定/給付年月

    // Field not in BADAPR

    // for PaymentQueryCpiData
    private String nowPayYm;// 本次給付年
    private BigDecimal lastIssueAmt; // 前次核定金額
    private BigDecimal nowIssueAmt; // 本次核定金額

    // for BaReportReplaceUtility
    private String minPayYm; // 最小給付年月
    private String maxPayYm; // 最大給付年月
    private BigDecimal sumAplpayAmt; //
    private BigDecimal sumIssueAmt; //
    private BigDecimal sumAdjustAmt; //
    private BigDecimal sumOtherAmt; //
    private BigDecimal sumSupAmt; //

    // for OtherRpt10
    private String chkMan;
    private String caseTyp;

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getEvTyp() {
        return evTyp;
    }

    public void setEvTyp(String evTyp) {
        this.evTyp = evTyp;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public Badapr() {

    }

    public BigDecimal getBadaprId() {
        return badaprId;
    }

    public BigDecimal getInsAvgAmt() {
        return insAvgAmt;
    }

    public void setInsAvgAmt(BigDecimal insAvgAmt) {
        this.insAvgAmt = insAvgAmt;
    }

    public BigDecimal getItrmY() {
        return itrmY;
    }

    public void setItrmY(BigDecimal itrmY) {
        this.itrmY = itrmY;
    }

    public BigDecimal getItrmD() {
        return itrmD;
    }

    public void setItrmD(BigDecimal itrmD) {
        this.itrmD = itrmD;
    }

    public BigDecimal getNitrmY() {
        return nitrmY;
    }

    public void setNitrmY(BigDecimal nitrmY) {
        this.nitrmY = nitrmY;
    }

    public BigDecimal getNitrmM() {
        return nitrmM;
    }

    public void setNitrmM(BigDecimal nitrmM) {
        this.nitrmM = nitrmM;
    }

    public BigDecimal getNoldtY() {
        return noldtY;
    }

    public void setNoldtY(BigDecimal noldtY) {
        this.noldtY = noldtY;
    }

    public BigDecimal getNoldtM() {
        return noldtM;
    }

    public void setNoldtM(BigDecimal noldtM) {
        this.noldtM = noldtM;
    }

    public BigDecimal getValseniY() {
        return valseniY;
    }

    public void setValseniY(BigDecimal valseniY) {
        this.valseniY = valseniY;
    }

    public BigDecimal getValseniM() {
        return valseniM;
    }

    public void setValseniM(BigDecimal valseniM) {
        this.valseniM = valseniM;
    }

    public BigDecimal getAplPaySeniY() {
        return aplPaySeniY;
    }

    public void setAplPaySeniY(BigDecimal aplPaySeniY) {
        this.aplPaySeniY = aplPaySeniY;
    }

    public BigDecimal getAplPaySeniM() {
        return aplPaySeniM;
    }

    public void setAplPaySeniM(BigDecimal aplPaySeniM) {
        this.aplPaySeniM = aplPaySeniM;
    }

    public BigDecimal getOldRate() {
        return oldRate;
    }

    public void setOldRate(BigDecimal oldRate) {
        this.oldRate = oldRate;
    }

    public String getOldRateSdate() {
        return oldRateSdate;
    }

    public void setOldRateSdate(String oldRateSdate) {
        this.oldRateSdate = oldRateSdate;
    }

    public String getOldRateEdate() {
        return oldRateEdate;
    }

    public void setOldRateEdate(String oldRateEdate) {
        this.oldRateEdate = oldRateEdate;
    }

    public void setBadaprId(BigDecimal badaprId) {
        this.badaprId = badaprId;
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

    public String getMtestMk() {
        return mtestMk;
    }

    public void setMtestMk(String mtestMk) {
        this.mtestMk = mtestMk;
    }

    public String getVeriSeq() {
        return veriSeq;
    }

    public void setVeriSeq(String veriSeq) {
        this.veriSeq = veriSeq;
    }

    public String getMchkTyp() {
        return mchkTyp;
    }

    public void setMchkTyp(String mchkTyp) {
        this.mchkTyp = mchkTyp;
    }

    public String getMapNo() {
        return mapNo;
    }

    public void setMapNo(String mapNo) {
        this.mapNo = mapNo;
    }

    public String getMaprootMk() {
        return maprootMk;
    }

    public void setMaprootMk(String maprootMk) {
        this.maprootMk = maprootMk;
    }

    public String getBenIds() {
        return benIds;
    }

    public void setBenIds(String benIds) {
        this.benIds = benIds;
    }

    public String getBenIdn() {
        return benIdn;
    }

    public void setBenIdn(String benIdn) {
        this.benIdn = benIdn;
    }

    public BigDecimal getOldSeni() {
        return oldSeni;
    }

    public void setOldSeni(BigDecimal oldSeni) {
        this.oldSeni = oldSeni;
    }

    public String getOldab() {
        return oldab;
    }

    public void setOldab(String oldab) {
        this.oldab = oldab;
    }

    public BigDecimal getOldaAmt() {
        return oldaAmt;
    }

    public void setOldaAmt(BigDecimal oldaAmt) {
        this.oldaAmt = oldaAmt;
    }

    public BigDecimal getOldbAmt() {
        return oldbAmt;
    }

    public void setOldbAmt(BigDecimal oldbAmt) {
        this.oldbAmt = oldbAmt;
    }

    public String getSuprecMk() {
        return suprecMk;
    }

    public void setSuprecMk(String suprecMk) {
        this.suprecMk = suprecMk;
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

    public BigDecimal getIssuCalcAmt() {
        return issuCalcAmt;
    }

    public void setIssuCalcAmt(BigDecimal issuCalcAmt) {
        this.issuCalcAmt = issuCalcAmt;
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

    public BigDecimal getAplpayAmt() {
        return aplpayAmt;
    }

    public void setAplpayAmt(BigDecimal aplpayAmt) {
        this.aplpayAmt = aplpayAmt;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        this.payRate = payRate;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public BigDecimal getUnitpayAmt() {
        return unitpayAmt;
    }

    public void setUnitpayAmt(BigDecimal unitpayAmt) {
        this.unitpayAmt = unitpayAmt;
    }

    public BigDecimal getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    public BigDecimal getRemainAmt() {
        return remainAmt;
    }

    public void setRemainAmt(BigDecimal remainAmt) {
        this.remainAmt = remainAmt;
    }

    public BigDecimal getCompenAmt() {
        return compenAmt;
    }

    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }

    public BigDecimal getLecomAmt() {
        return lecomAmt;
    }

    public void setLecomAmt(BigDecimal lecomAmt) {
        this.lecomAmt = lecomAmt;
    }

    public BigDecimal getRecomAmt() {
        return recomAmt;
    }

    public void setRecomAmt(BigDecimal recomAmt) {
        this.recomAmt = recomAmt;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public BigDecimal getRecapAmt() {
        return recapAmt;
    }

    public void setRecapAmt(BigDecimal recapAmt) {
        this.recapAmt = recapAmt;
    }

    public BigDecimal getLoaniTrt() {
        return loaniTrt;
    }

    public void setLoaniTrt(BigDecimal loaniTrt) {
        this.loaniTrt = loaniTrt;
    }

    public String getDlineDate() {
        return dlineDate;
    }

    public void setDlineDate(String dlineDate) {
        this.dlineDate = dlineDate;
    }

    public BigDecimal getOffsetExp() {
        return offsetExp;
    }

    public void setOffsetExp(BigDecimal offsetExp) {
        this.offsetExp = offsetExp;
    }

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getRemitAmt() {
        return remitAmt;
    }

    public void setRemitAmt(BigDecimal remitAmt) {
        this.remitAmt = remitAmt;
    }

    public BigDecimal getOvrcutAmt() {
        return ovrcutAmt;
    }

    public void setOvrcutAmt(BigDecimal ovrcutAmt) {
        this.ovrcutAmt = ovrcutAmt;
    }

    public BigDecimal getPayBanance() {
        return payBanance;
    }

    public void setPayBanance(BigDecimal payBanance) {
        this.payBanance = payBanance;
    }

    public String getAplpayMk() {
        return aplpayMk;
    }

    public void setAplpayMk(String aplpayMk) {
        this.aplpayMk = aplpayMk;
    }

    public String getAplpayDate() {
        return aplpayDate;
    }

    public void setAplpayDate(String aplpayDate) {
        this.aplpayDate = aplpayDate;
    }

    public String getGenmedDate() {
        return genmedDate;
    }

    public void setGenmedDate(String genmedDate) {
        this.genmedDate = genmedDate;
    }

    public String getRemitMk() {
        return remitMk;
    }

    public void setRemitMk(String remitMk) {
        this.remitMk = remitMk;
    }

    public String getRemitDate() {
        return remitDate;
    }

    public void setRemitDate(String remitDate) {
        this.remitDate = remitDate;
    }

    public String getTransActiond() {
        return transActiond;
    }

    public void setTransActiond(String transActiond) {
        this.transActiond = transActiond;
    }

    public String getStexpndMk() {
        return stexpndMk;
    }

    public void setStexpndMk(String stexpndMk) {
        this.stexpndMk = stexpndMk;
    }

    public String getStexpndReason() {
        return stexpndReason;
    }

    public void setStexpndReason(String stexpndReason) {
        this.stexpndReason = stexpndReason;
    }

    public String getStexpndDate() {
        return stexpndDate;
    }

    public void setStexpndDate(String stexpndDate) {
        this.stexpndDate = stexpndDate;
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

    public String getCprnDate() {
        return cprnDate;
    }

    public void setCprnDate(String cprnDate) {
        this.cprnDate = cprnDate;
    }

    public BigDecimal getCprnPage() {
        return cprnPage;
    }

    public void setCprnPage(BigDecimal cprnPage) {
        this.cprnPage = cprnPage;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }

    public String getPayYmChineseString() {
        return DateUtil.changeWestYearMonthType(payYm);
    }

    public String getBureauAcc() {
        return bureauAcc;
    }

    public void setBureauAcc(String bureauAcc) {
        this.bureauAcc = bureauAcc;
    }

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }

    public BigDecimal getEvtOffsetAmt() {
        return evtOffsetAmt;
    }

    public void setEvtOffsetAmt(BigDecimal evtOffsetAmt) {
        this.evtOffsetAmt = evtOffsetAmt;
    }

    public BigDecimal getBadDebtAmt() {
        return badDebtAmt;
    }

    public void setBadDebtAmt(BigDecimal badDebtAmt) {
        this.badDebtAmt = badDebtAmt;
    }

    public String getPayYmBegin() {
        return payYmBegin;
    }

    public void setPayYmBegin(String payYmBegin) {
        this.payYmBegin = payYmBegin;
    }

    public String getPayYmEnd() {
        return payYmEnd;
    }

    public void setPayYmEnd(String payYmEnd) {
        this.payYmEnd = payYmEnd;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getStexpnd() {
        return stexpnd;
    }

    public void setStexpnd(String stexpnd) {
        this.stexpnd = stexpnd;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
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

    public BigDecimal getBadaprIdAmt() {
        return badaprIdAmt;
    }

    public void setBadaprIdAmt(BigDecimal badaprIdAmt) {
        this.badaprIdAmt = badaprIdAmt;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getOtherAamt() {
        return otherAamt;
    }

    public void setOtherAamt(BigDecimal otherAamt) {
        this.otherAamt = otherAamt;
    }

    public BigDecimal getOtherBamt() {
        return otherBamt;
    }

    public void setOtherBamt(BigDecimal otherBamt) {
        this.otherBamt = otherBamt;
    }

    public BigDecimal getPay1() {
        return pay1;
    }

    public void setPay1(BigDecimal pay1) {
        this.pay1 = pay1;
    }

    public BigDecimal getPay2() {
        return pay2;
    }

    public void setPay2(BigDecimal pay2) {
        this.pay2 = pay2;
    }

    public BigDecimal getPay3() {
        return pay3;
    }

    public void setPay3(BigDecimal pay3) {
        this.pay3 = pay3;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
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

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public BigDecimal getOcAccaddAmt() {
        return ocAccaddAmt;
    }

    public void setOcAccaddAmt(BigDecimal ocAccaddAmt) {
        this.ocAccaddAmt = ocAccaddAmt;
    }

    public String getPayYmStr() {
        return payYmStr;
    }

    public void setPayYmStr(String payYmStr) {
        this.payYmStr = payYmStr;
    }

    public BigDecimal getOldAmt() {
        return oldAmt;
    }

    public void setOldAmt(BigDecimal oldAmt) {
        this.oldAmt = oldAmt;
    }

    public BigDecimal getQualCount() {
        return qualCount;
    }

    public void setQualCount(BigDecimal qualCount) {
        this.qualCount = qualCount;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
    }

    public BigDecimal getOldExtraRate() {
        return oldExtraRate;
    }

    public void setOldExtraRate(BigDecimal oldExtraRate) {
        this.oldExtraRate = oldExtraRate;
    }

    public BigDecimal getRecAmtEvt() {
        return recAmtEvt;
    }

    public void setRecAmtEvt(BigDecimal recAmtEvt) {
        this.recAmtEvt = recAmtEvt;
    }

    public BigDecimal getRecAmtBen() {
        return recAmtBen;
    }

    public void setRecAmtBen(BigDecimal recAmtBen) {
        this.recAmtBen = recAmtBen;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getIssuYmStr() {
        return issuYmStr;
    }

    public void setIssuYmStr(String issuYmStr) {
        this.issuYmStr = issuYmStr;
    }

    public String getIssuPayYm() {
        return issuPayYm;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public void setIssuPayYm(String issuPayYm) {
        this.issuPayYm = issuPayYm;
    }

    public BigDecimal getOtherAmtEvt() {
        return otherAmtEvt;
    }

    public void setOtherAmtEvt(BigDecimal otherAmtEvt) {
        this.otherAmtEvt = otherAmtEvt;
    }

    public BigDecimal getOtherAmtBen() {
        return otherAmtBen;
    }

    public void setOtherAmtBen(BigDecimal otherAmtBen) {
        this.otherAmtBen = otherAmtBen;
    }

    public BigDecimal getCpiRate() {
        return cpiRate;
    }

    public void setCpiRate(BigDecimal cpiRate) {
        this.cpiRate = cpiRate;
    }

    public String getNowPayYm() {
        return nowPayYm;
    }

    public void setNowPayYm(String nowPayYm) {
        this.nowPayYm = nowPayYm;
    }

    public BigDecimal getLastIssueAmt() {
        return lastIssueAmt;
    }

    public void setLastIssueAmt(BigDecimal lastIssueAmt) {
        this.lastIssueAmt = lastIssueAmt;
    }

    public BigDecimal getNowIssueAmt() {
        return nowIssueAmt;
    }

    public void setNowIssueAmt(BigDecimal nowIssueAmt) {
        this.nowIssueAmt = nowIssueAmt;
    }

    public BigDecimal getEvtOtherAmt() {
        return evtOtherAmt;
    }

    public void setEvtOtherAmt(BigDecimal evtOtherAmt) {
        this.evtOtherAmt = evtOtherAmt;
    }

    public String getOriIssuYm() {
        return oriIssuYm;
    }

    public void setOriIssuYm(String oriIssuYm) {
        this.oriIssuYm = oriIssuYm;
    }

    public String getHeirSeqNo() {
        return heirSeqNo;
    }

    public void setHeirSeqNo(String heirSeqNo) {
        this.heirSeqNo = heirSeqNo;
    }

    public BigDecimal getAvgNum() {
        return avgNum;
    }

    public void setAvgNum(BigDecimal avgNum) {
        this.avgNum = avgNum;
    }

    public String getMinPayYm() {
        return minPayYm;
    }

    public void setMinPayYm(String minPayYm) {
        this.minPayYm = minPayYm;
    }

    public String getMaxPayYm() {
        return minPayYm;
    }

    public void setMaxPayYm(String minPayYm) {
        this.minPayYm = minPayYm;
    }

    public BigDecimal getSumAplpayAmt() {
        return sumAplpayAmt;
    }

    public void setSumAplpayAmt(BigDecimal sumAplpayAmt) {
        this.sumAplpayAmt = sumAplpayAmt;
    }

    public BigDecimal getSumIssueAmt() {
        return sumIssueAmt;
    }

    public void setSumIssueAmt(BigDecimal sumIssueAmt) {
        this.sumIssueAmt = sumIssueAmt;
    }

    public BigDecimal getSumAdjustAmt() {
        return sumAdjustAmt;
    }

    public void setSumAdjustAmt(BigDecimal sumAdjustAmt) {
        this.sumAdjustAmt = sumAdjustAmt;
    }

    public BigDecimal getSumOtherAmt() {
        return sumOtherAmt;
    }

    public void setSumOtherAmt(BigDecimal sumOtherAmt) {
        this.sumOtherAmt = sumOtherAmt;
    }

    public BigDecimal getSumSupAmt() {
        return sumSupAmt;
    }

    public void setSumSupAmt(BigDecimal sumSupAmt) {
        this.sumSupAmt = sumSupAmt;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public BigDecimal getInheritorAmt() {
        return inheritorAmt;
    }

    public void setInheritorAmt(BigDecimal inheritorAmt) {
        this.inheritorAmt = inheritorAmt;
    }

}
