package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 給付查詢作業 請領同類／他類／另案扣減
 * 
 * @author Rickychi
 */
public class PaymentQueryApplyDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    // 請領同類／他類／另案扣減
    // [
    // 一次給付資料
    private String bmApNo; // 受理編號
    private String bmApDte; // 受理日期
    private String bmEvName; // 事故者姓名
    private String bmEvtDte; // 事故日期
    private BigDecimal bmChkAmt; // 核定金額
    private String bmExmDte; // 核定日期(複核日期)
    private String bmPayDte; // 核付日期
    private String bmNrepDate; // 補件日期
    private String bmNdocMk; // 補件註記
    private String bmNopDate; // 不給付日期
    private BigDecimal bmAdjAmts; // 補收金額
    private String bmUbNo; // 保險證號

    // 年金給付資料
    private BigDecimal baappbaseId;
    private String apNo; // 受理編號
    private String evtName; // 事故者姓名
    private String appDate; // 申請日期
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String evtDate; // 事故日期
    private String lsUbno; // 保險證號
    private BigDecimal issueAmt; // 核定金額
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate;// 事故者出生日期
    private String exeDate; // 不給付日期
    private String proDate; // 補件日期
    private String ndomk1; // 處理註記
    private String chkDate; // 核定日期
    private String aplPayDate; // 核付日期
    private BigDecimal recAmt; // 補收金額
    private BigDecimal supAmt; // 已收金額

    // 申請失能給付紀錄
    // private String bmApNo; // 受理編號
    // private String bmApDte; // 受理日期
    // private String bmEvName; // 事故者姓名
    // private String bmEvtDte; // 診斷失能日期
    // private BigDecimal bmChkAmt; // 核定金額
    // private String bmExmDte; // 核定日期
    // private String bmPayDte; // 核付日期
    // private String bmNrepDate; // 補件日期
    // private String bmNdocMk; // 補件註記
    // private String bmNopDate; // 不給付日期
    private String bmCriInjCl; // 失能項目
    private String bmCriInjDp; // 失能等級

    // 申請死亡給付紀錄
    // private String bmApNo; // 受理編號
    // private String bmApDte; // 申請日期
    // private String bmEvName; // 事故者姓名
    // private String bmEvtDte; // 死亡日期
    // private BigDecimal bmChkAmt; // 核定金額
    // private String bmExmDte; // 核定日期
    // private String bmPayDte; // 核付日期
    // private String bmNrepDate; // 補件日期
    // private String bmNdocMk;// 補件註記
    // private String bmNopDate; // 不給付日期

    // 申請傷病給付紀錄
    // private String bmApNo; // 受理編號
    // private String bmApDte; // 申請日期
    // private String bmEvName; // 事故者姓名
    // private String bmEvtDte; // 事故日期
    // private BigDecimal bmChkAmt; // 核定金額
    // private String bmExmDte; // 核定日期
    // private String bmPayDte; // 核付日期
    // private String bmNrepDate; // 補件日期
    // private String bmNdocMk; // 補件註記
    // private String bmNopDate; // 不給付日期
    private String bmLosFmDte; // 核定起日
    private String bmLosToDte; // 核定迄日

    // 申請失業給付紀錄
    // private String apNo; // 受理編號
    private String symDte; // 給付起日
    private String tymDte; // 給付迄日
    private String apDte; // 申請日期
    private String name; // 被保險人姓名
    private String tdte; // 事故日期
    private BigDecimal chkAmt; // 核定金額
    private String chkDte; // 核定日期
    private String payDte; // 核付日期
    private String npyDte; // 不給付日期
    private String aprDte;// 求職日期
    private String ndcMrk;// 補件註記
    private String savDt1;// 補件日期

    // 申請國保給付記錄
    // private String appDate; // 申請日期(收件日期)
    // private String apNo; // 受理編號
    // private String issuYm; // 核定年月
    // private String payYm; // 給付年月
    private String evteeName; // 事故者姓名
    private String evtDt; // 事故日期
    // private BigDecimal issueAmt; // 核定金額
    private String manChkFlg; // 人工審核結果
    private String acceptMark; // 合格註記
    private String chkDt;// 核定日期

    // 頁面顯示轉換
    // [
    public String getBmApNoStr() {
        if (StringUtils.isNotBlank(getBmApNo()) && getBmApNo().length() == ConstantKey.APNO_LENGTH) {
            return getBmApNo().substring(0, 1) + "-" + getBmApNo().substring(1, 2) + "-" + getBmApNo().substring(2, 7) + "-" + getBmApNo().substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getBmApDteStr() {
        if (StringUtils.isNotBlank(getBmApDte())) {
            return DateUtility.changeDateType(getBmApDte());
        }
        else {
            return "";
        }
    }

    public String getBmEvtDteStr() {
        if (StringUtils.isNotBlank(getBmEvtDte())) {
            return DateUtility.changeDateType(getBmEvtDte());
        }
        else {
            return "";
        }
    }

    public String getBmExmDteStr() {
        if (StringUtils.isNotBlank(getBmExmDte())) {
            return DateUtility.changeDateType(getBmExmDte());
        }
        else {
            return "";
        }
    }

    public String getBmPayDteStr() {
        if (StringUtils.isNotBlank(getBmPayDte())) {
            return DateUtility.changeDateType(getBmPayDte());
        }
        else {
            return "";
        }
    }

    public String getBmNrepDateStr() {
        if (StringUtils.isNotBlank(getBmNrepDate())) {
            return DateUtility.changeDateType(getBmNrepDate());
        }
        else {
            return "";
        }
    }

    public String getBmNopDateStr() {
        if (StringUtils.isNotBlank(getBmNopDate())) {
            return DateUtility.changeDateType(getBmNopDate());
        }
        else {
            return "";
        }
    }

    public String getApNoStr() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH) {
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        }
        else {
            return "";
        }
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
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

    public String getEvtDateStr() {
        if (StringUtils.isNotBlank(getEvtDate())) {
            return DateUtility.changeDateType(getEvtDate());
        }
        else {
            return "";
        }
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getEvtBrDate());
        }
        else {
            return "";
        }
    }

    public String getExeDateStr() {
        if (StringUtils.isNotBlank(getExeDate())) {
            return DateUtility.changeDateType(getExeDate());
        }
        else {
            return "";
        }
    }

    public String getProDateStr() {
        if (StringUtils.isNotBlank(getProDate())) {
            return DateUtility.changeDateType(getProDate());
        }
        else {
            return "";
        }
    }

    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate())) {
            return DateUtility.changeDateType(getChkDate());
        }
        else {
            return "";
        }
    }

    public String getAplPayDateStr() {
        if (StringUtils.isNotBlank(getAplPayDate())) {
            return DateUtility.changeDateType(getAplPayDate());
        }
        else {
            return "";
        }
    }

    public String getBmLosFmDteStr() {
        if (StringUtils.isNotBlank(getBmLosFmDte())) {
            return DateUtility.changeDateType(getBmLosFmDte());
        }
        else {
            return "";
        }
    }

    public String getBmLosToDteStr() {
        if (StringUtils.isNotBlank(getBmLosToDte())) {
            return DateUtility.changeDateType(getBmLosToDte());
        }
        else {
            return "";
        }
    }

    public String getSymDteStr() {
        if (StringUtils.isNotBlank(getSymDte())) {
            return DateUtility.changeDateType(getSymDte());
        }
        else {
            return "";
        }
    }

    public String getTymDteStr() {
        if (StringUtils.isNotBlank(getTymDte())) {
            return DateUtility.changeDateType(getTymDte());
        }
        else {
            return "";
        }
    }

    public String getApDteStr() {
        if (StringUtils.isNotBlank(getApDte())) {
            return DateUtility.changeDateType(getApDte());
        }
        else {
            return "";
        }
    }

    public String getTdteStr() {
        if (StringUtils.isNotBlank(getTdte())) {
            return DateUtility.changeDateType(getTdte());
        }
        else {
            return "";
        }
    }

    public String getChkDteStr() {
        if (StringUtils.isNotBlank(getChkDte())) {
            return DateUtility.changeDateType(getChkDte());
        }
        else {
            return "";
        }
    }

    public String getPayDteStr() {
        if (StringUtils.isNotBlank(getPayDte())) {
            return DateUtility.changeDateType(getPayDte());
        }
        else {
            return "";
        }
    }

    public String getNpyDteStr() {
        if (StringUtils.isNotBlank(getNpyDte())) {
            return DateUtility.changeDateType(getNpyDte());
        }
        else {
            return "";
        }
    }

    public String getAprDteStr() {
        if (StringUtils.isNotBlank(getAprDte())) {
            return DateUtility.changeDateType(getAprDte());
        }
        else {
            return "";
        }
    }

    public String getSavDt1Str() {
        if (StringUtils.isNotBlank(getSavDt1())) {
            return DateUtility.changeDateType(getSavDt1());
        }
        else {
            return "";
        }
    }

    public String getEvtDtStr() {
        if (StringUtils.isNotBlank(getEvtDt())) {
            return DateUtility.changeDateType(getEvtDt());
        }
        else {
            return "";
        }
    }

    public String getChkDtStr() {
        if (StringUtils.isNotBlank(getChkDt())) {
            return DateUtility.changeDateType(getChkDt());
        }
        else {
            return "";
        }
    }

    // ]
    public String getBmApNo() {
        return bmApNo;
    }

    public void setBmApNo(String bmApNo) {
        this.bmApNo = bmApNo;
    }

    public String getBmApDte() {
        return bmApDte;
    }

    public void setBmApDte(String bmApDte) {
        this.bmApDte = bmApDte;
    }

    public String getBmEvName() {
        return bmEvName;
    }

    public void setBmEvName(String bmEvName) {
        this.bmEvName = bmEvName;
    }

    public String getBmEvtDte() {
        return bmEvtDte;
    }

    public void setBmEvtDte(String bmEvtDte) {
        this.bmEvtDte = bmEvtDte;
    }

    public BigDecimal getBmChkAmt() {
        return bmChkAmt;
    }

    public void setBmChkAmt(BigDecimal bmChkAmt) {
        this.bmChkAmt = bmChkAmt;
    }

    public String getBmExmDte() {
        return bmExmDte;
    }

    public void setBmExmDte(String bmExmDte) {
        this.bmExmDte = bmExmDte;
    }

    public String getBmPayDte() {
        return bmPayDte;
    }

    public void setBmPayDte(String bmPayDte) {
        this.bmPayDte = bmPayDte;
    }

    public String getBmNrepDate() {
        return bmNrepDate;
    }

    public void setBmNrepDate(String bmNrepDate) {
        this.bmNrepDate = bmNrepDate;
    }

    public String getBmNdocMk() {
        return bmNdocMk;
    }

    public void setBmNdocMk(String bmNdocMk) {
        this.bmNdocMk = bmNdocMk;
    }

    public String getBmNopDate() {
        return bmNopDate;
    }

    public void setBmNopDate(String bmNopDate) {
        this.bmNopDate = bmNopDate;
    }

    public BigDecimal getBmAdjAmts() {
        return bmAdjAmts;
    }

    public void setBmAdjAmts(BigDecimal bmAdjAmts) {
        this.bmAdjAmts = bmAdjAmts;
    }

    public String getBmUbNo() {
        return bmUbNo;
    }

    public void setBmUbNo(String bmUbNo) {
        this.bmUbNo = bmUbNo;
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

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
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

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
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

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

    public String getProDate() {
        return proDate;
    }

    public void setProDate(String proDate) {
        this.proDate = proDate;
    }

    public String getNdomk1() {
        return ndomk1;
    }

    public void setNdomk1(String ndomk1) {
        this.ndomk1 = ndomk1;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getAplPayDate() {
        return aplPayDate;
    }

    public void setAplPayDate(String aplPayDate) {
        this.aplPayDate = aplPayDate;
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

    public String getBmCriInjCl() {
        return bmCriInjCl;
    }

    public void setBmCriInjCl(String bmCriInjCl) {
        this.bmCriInjCl = bmCriInjCl;
    }

    public String getBmCriInjDp() {
        return bmCriInjDp;
    }

    public void setBmCriInjDp(String bmCriInjDp) {
        this.bmCriInjDp = bmCriInjDp;
    }

    public String getBmLosFmDte() {
        return bmLosFmDte;
    }

    public void setBmLosFmDte(String bmLosFmDte) {
        this.bmLosFmDte = bmLosFmDte;
    }

    public String getBmLosToDte() {
        return bmLosToDte;
    }

    public void setBmLosToDte(String bmLosToDte) {
        this.bmLosToDte = bmLosToDte;
    }

    public String getSymDte() {
        return symDte;
    }

    public void setSymDte(String symDte) {
        this.symDte = symDte;
    }

    public String getTymDte() {
        return tymDte;
    }

    public void setTymDte(String tymDte) {
        this.tymDte = tymDte;
    }

    public String getApDte() {
        return apDte;
    }

    public void setApDte(String apDte) {
        this.apDte = apDte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTdte() {
        return tdte;
    }

    public void setTdte(String tdte) {
        this.tdte = tdte;
    }

    public BigDecimal getChkAmt() {
        return chkAmt;
    }

    public void setChkAmt(BigDecimal chkAmt) {
        this.chkAmt = chkAmt;
    }

    public String getChkDte() {
        return chkDte;
    }

    public void setChkDte(String chkDte) {
        this.chkDte = chkDte;
    }

    public String getPayDte() {
        return payDte;
    }

    public void setPayDte(String payDte) {
        this.payDte = payDte;
    }

    public String getNpyDte() {
        return npyDte;
    }

    public void setNpyDte(String npyDte) {
        this.npyDte = npyDte;
    }

    public String getAprDte() {
        return aprDte;
    }

    public void setAprDte(String aprDte) {
        this.aprDte = aprDte;
    }

    public String getNdcMrk() {
        return ndcMrk;
    }

    public void setNdcMrk(String ndcMrk) {
        this.ndcMrk = ndcMrk;
    }

    public String getSavDt1() {
        return savDt1;
    }

    public void setSavDt1(String savDt1) {
        this.savDt1 = savDt1;
    }

    public String getEvteeName() {
        return evteeName;
    }

    public void setEvteeName(String evteeName) {
        this.evteeName = evteeName;
    }

    public String getEvtDt() {
        return evtDt;
    }

    public void setEvtDt(String evtDt) {
        this.evtDt = evtDt;
    }

    public String getManChkFlg() {
        return manChkFlg;
    }

    public void setManChkFlg(String manChkFlg) {
        this.manChkFlg = manChkFlg;
    }

    public String getAcceptMark() {
        return acceptMark;
    }

    public void setAcceptMark(String acceptMark) {
        this.acceptMark = acceptMark;
    }

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
        this.chkDt = chkDt;
    }

    // private String aplPayDate;// 核付日期
    // ]
}
