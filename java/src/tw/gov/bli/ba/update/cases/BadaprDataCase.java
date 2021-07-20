package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

public class BadaprDataCase implements Serializable {
    private static final long serialVersionUID = -5056817223316820001L;

    private BigDecimal badaprId; // 資料列編號
    private BigDecimal baappbaseId; // 給付主檔資料編號
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
    private BigDecimal badDebtAmt; // 呆帳金額
    private BigDecimal befIssueAmt;
    private BigDecimal ocAccaddAmt; // 已領職災增給金額

    // 主檔欄位
    private String apItem;// 申請項目

    public BigDecimal getBadaprId() {
        return badaprId;
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

    public BigDecimal getBadDebtAmt() {
        return badDebtAmt;
    }

    public void setBadDebtAmt(BigDecimal badDebtAmt) {
        this.badDebtAmt = badDebtAmt;
    }

    public String getIssuYmStr() {
        return DateUtility.changeWestYearMonthType(issuYm);
    }

    public String getNitrmYM() {
        if (nitrmY != null)
            if (itrmY != null)
                return nitrmY.toString() + "年" + nitrmM.toString() + "月(" + itrmY.toString() + "年" + itrmD.toString() + "日)";
            else
                return nitrmY.toString() + "年" + nitrmM.toString() + "月";
        else
            return "";
    }

    public String getNoldtYM() {
        if (noldtY != null)
            return noldtY.toString() + "年" + noldtM.toString() + "月";
        else
            return "";
    }

    public String getValseniYM() {
        if (valseniY != null)
            return valseniY.toString() + "年" + valseniM.toString() + "月";
        else
            return "";
    }

    public String getAplPaySeniYM() {
        if (aplPaySeniY != null)
            return aplPaySeniY.toString() + "年" + aplPaySeniM.toString() + "月";
        else
            return "";
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getOldRateSdateString() {
        if (oldRateSdate != null)
            return DateUtility.changeWestYearMonthType(oldRateSdate);
        else
            return "";
    }

    public String getOldRateEdateString() {
        if (oldRateEdate != null)
            return DateUtility.changeWestYearMonthType(oldRateEdate);
        else
            return "";
    }

    public String getOldRateDateString() {
        String oldRateDateYMStr = "";
        if (StringUtils.isNotBlank(oldRateSdate) && StringUtils.isNotBlank(oldRateEdate)) {
            int oldRateDateYM = DateUtility.wholeMonthsBetween(oldRateSdate + "01", oldRateEdate + "01");
            if (oldRateDateYM > 0) {
                oldRateDateYMStr = oldRateDateYM / 12 + "年 " + oldRateDateYM % 12 + "月";
            }
        }
        return oldRateDateYMStr;
    }

    public BigDecimal getBefIssueAmt() {
        return befIssueAmt;
    }

    public void setBefIssueAmt(BigDecimal befIssueAmt) {
        this.befIssueAmt = befIssueAmt;
    }

    public BigDecimal getOcAccaddAmt() {
        return ocAccaddAmt;
    }

    public void setOcAccaddAmt(BigDecimal ocAccaddAmt) {
        this.ocAccaddAmt = ocAccaddAmt;
    }
}
