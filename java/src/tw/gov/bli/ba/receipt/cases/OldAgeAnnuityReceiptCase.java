package tw.gov.bli.ba.receipt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 老年年金受理作業
 * 
 * @author Rickychi
 */
public class OldAgeAnnuityReceiptCase implements Serializable {
    private BigDecimal baappbaseId;// 資料列編號
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String imk;// 保險別
    private String apItem;// 申請項目
    private String payKind;// 給付種類
    private String appDate;// 申請日期
    private String caseTyp;// 案件類別
    private String mapNo;// 相關受理編號
    private String mapRootMk;// 相關受理編號註記
    private String combapMark;// 國勞合併申請註記
    private String caseMk;// 案件註記
    private String procStat;// 處理狀態
    private String acceptMk;// 合格註記
    private String manchkMk;// 人工審核結果
    private String chgNote;// 更正原因
    private String exeStat;// 決行狀況
    private String apUbno;// 申請單位保險證號
    private String apubnock;// 申請單位保險證號檢查碼
    private String lsUbno;// 最後單位保險證號
    private String lsUbnoCk;// 最後單位保險證號檢查碼
    private String evtIds;// 事故者社福識別碼
    private String evtIdnNo;// 事故者身分證號
    private String evtName;// 事故者姓名
    private String evtBrDate;// 事故者出生日期
    private String evtSex;// 事故者性別
    private String evtNationTpe;// 事故者國籍別
    private String evtNationCode;// 事故者國籍
    private String evtIdent;// 事故者身分別
    private String evtJobDate;// 事故者離職日期
    private String evtDate;// 事故日期
    private String evtDieDate;// 事故者死亡日期
    private String evtAge;// 事故者申請時年齡
    private String benIds;// 受益人社福識別碼
    private String benIdnNo;// 受益人身分證號
    private String benName;// 受益人姓名
    private String benBrDate;// 受益人出生日期
    private String benSex;// 受益人性別
    private String benNationTyp;// 受益人國籍別
    private String benNationCode;// 受益人國籍
    private String benEvtRel;// 受益人與事故者關係
    private String benDieDate;// 受益人死亡日期
    private String benPayMk;// 受益人領取狀態註記
    private String idnChkYm;// 身分查核年月
    private String idnChkNote;// 身分查核註記
    private String tel1;// 電話1
    private String tel2;// 電話2
    private String commTyp;// 通訊地址別
    private String commZip;// 通訊郵遞區號
    private String commAddr;// 通訊地址
    private String payTyp;// 給付方式
    private String bankName;// 金融機構名稱
    private String payBankId;// 金融機構總代號
    private String branchId;// 分支代號
    private String payEeacc;// 銀行帳號 (帳號(後))
    private String chkPayBankId;// 金融機構總代號 複驗
    private String chkBranchId;// 分支代號 複驗
    private String chkPayEeacc;// 銀行帳號 (帳號(後)) 複驗
    
    private String accIdn;// 戶名IDN
    private String accName;// 戶名
    private String accRel;// 戶名與受益人關係
    private BigDecimal mitRate;// 匯款匯費
    private String grdIdnNo;// 法定代理人身分證號
    private String grdName;// 法定代理人姓名
    private String grdBrDate;// 法定代理人出生日期
    private String assignIdnNo;// 受委託人身分證號
    private String assignName;// 受委託人姓名
    private String assignBrDate;// 受委託人出生日期
    private String issuYm;// 核定年月
    private String payYm;// 給付年月
    private BigDecimal issueAmt;// 核定金額
    private BigDecimal onceAmt;// 一次給付核定金額
    private BigDecimal onceAvgAmt;// 一次給付平均薪資
    private BigDecimal onceAplPayAmt;// 一次給付實發年資
    private String oncePayMk;// 一次給付符合註記
    private BigDecimal onceOldAmt;// 老年一次金金額
    private String oldSeniab;// 一次請領之年資採計方式
    private BigDecimal mustIssueAmt;// 可領金額
    private String payDate;// 首次核定日期
    private String payYms;// 首次發放起月
    private String payYme;// 首次發放迄月
    private String pauseYm;// 暫停續發年月
    private String unPauseYm;// 恢復續發年月
    private String notifyForm;// 核定通知書格式
    private String loanMk;// 不須抵銷紓困貸款註記
    private String mexcLvl;// 應決行層級
    private String realexcLvl;// 實際決行層級
    private String chkDate;// 審核日期
    private String chkMan;// 審核人員
    private String rechkDate;// 複核日期
    private String rechkMan;// 複核人員
    private String exeDate;// 決行日期
    private String exeMan;// 決行人員
    private String arcDate;// 歸檔日期
    private String arcPg;// 歸檔頁次
    private String closeDate;// 結案日期
    private String closeCause;// 結案原因
    private String promoteUser;// 承辦者代號
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    private String updUser;// 異動者代號
    private String updTime;// 異動日期時間
    private String dupeIdnNoMk;// 身分證重號註記
    private String evtExpireDate; // 事故者屆齡日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期
    private BigDecimal lsInsmAmt; // 投保金額
    private String lsChkMk;// 勞貸戶註記
    private BigDecimal lsCount;// 勞貸戶筆數
    private String lnChkMk;// 紓困貸款呆帳債務人註記
    private String dabApNo;// 已領失能年金受理編號
    private BigDecimal dabAnnuAmt;// 已領失能年金金額
    private String evtAppName;// 事故者申請時姓名

    private String apNo1;// 受理編號-1
    private String apNo2;// 受理編號-2
    private String apNo3;// 受理編號-3
    private String apNo4;// 受理編號-4
    private String payBankIdBranchId;// 帳號(前)
    private String chkPayBankIdChkBranchId;// 帳號(前) 複驗
    private String cvldtlName;// 戶籍姓名
    private String mobilePhone; // 手機複驗
    private String specialAcc;	// 專戶			EthanChen; 20210512; for ba_11004227619

    private List<Baapplog> baapplogList;// 更正記錄檔

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return "";
        }
    }

    public String getEvtBrDateStr() {
        if (StringUtils.isNotBlank(getEvtBrDate())) {
            return DateUtility.changeDateType(getEvtBrDate());
        }
        else {
            return "";
        }
    }

    public String getEvtJobDateStr() {
        if (StringUtils.isNotBlank(getEvtJobDate())) {
            return DateUtility.changeDateType(getEvtJobDate());
        }
        else {
            return "";
        }
    }

    public String getGrdBrDateStr() {
        if (StringUtils.isNotBlank(getGrdBrDate())) {
            return DateUtility.changeDateType(getGrdBrDate());
        }
        else {
            return "";
        }
    }

    public String getApNoStr() {
        return getApNo1() + getApNo2() + getApNo3() + getApNo4();
    }

    public String getApNoStrDisplay() {
        return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
    }

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
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

    public String getImk() {
        return imk;
    }

    public void setImk(String imk) {
        this.imk = imk;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getMapNo() {
        return mapNo;
    }

    public void setMapNo(String mapNo) {
        this.mapNo = mapNo;
    }

    public String getMapRootMk() {
        return mapRootMk;
    }

    public void setMapRootMk(String mapRootMk) {
        this.mapRootMk = mapRootMk;
    }

    public String getCombapMark() {
        return combapMark;
    }

    public void setCombapMark(String combapMark) {
        this.combapMark = combapMark;
    }

    public String getCaseMk() {
        return caseMk;
    }

    public void setCaseMk(String caseMk) {
        this.caseMk = caseMk;
    }

    public String getProcStat() {
        return procStat;
    }

    public void setProcStat(String procStat) {
        this.procStat = procStat;
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

    public String getChgNote() {
        return chgNote;
    }

    public void setChgNote(String chgNote) {
        this.chgNote = chgNote;
    }

    public String getExeStat() {
        return exeStat;
    }

    public void setExeStat(String exeStat) {
        this.exeStat = exeStat;
    }

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getApubnock() {
        return apubnock;
    }

    public void setApubnock(String apubnock) {
        this.apubnock = apubnock;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public String getLsUbnoCk() {
        return lsUbnoCk;
    }

    public void setLsUbnoCk(String lsUbnoCk) {
        this.lsUbnoCk = lsUbnoCk;
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

    public String getEvtSex() {
        return evtSex;
    }

    public void setEvtSex(String evtSex) {
        this.evtSex = evtSex;
    }

    public String getEvtNationTpe() {
        return evtNationTpe;
    }

    public void setEvtNationTpe(String evtNationTpe) {
        this.evtNationTpe = evtNationTpe;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public String getEvtIdent() {
        return evtIdent;
    }

    public void setEvtIdent(String evtIdent) {
        this.evtIdent = evtIdent;
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

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getEvtAge() {
        return evtAge;
    }

    public void setEvtAge(String evtAge) {
        this.evtAge = evtAge;
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

    public String getBenSex() {
        return benSex;
    }

    public void setBenSex(String benSex) {
        this.benSex = benSex;
    }

    public String getBenNationTyp() {
        return benNationTyp;
    }

    public void setBenNationTyp(String benNationTyp) {
        this.benNationTyp = benNationTyp;
    }

    public String getBenNationCode() {
        return benNationCode;
    }

    public void setBenNationCode(String benNationCode) {
        this.benNationCode = benNationCode;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getBenDieDate() {
        return benDieDate;
    }

    public void setBenDieDate(String benDieDate) {
        this.benDieDate = benDieDate;
    }

    public String getBenPayMk() {
        return benPayMk;
    }

    public void setBenPayMk(String benPayMk) {
        this.benPayMk = benPayMk;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public String getIdnChkNote() {
        return idnChkNote;
    }

    public void setIdnChkNote(String idnChkNote) {
        this.idnChkNote = idnChkNote;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCommTyp() {
        return commTyp;
    }

    public void setCommTyp(String commTyp) {
        this.commTyp = commTyp;
    }

    public String getCommZip() {
        return commZip;
    }

    public void setCommZip(String commZip) {
        this.commZip = commZip;
    }

    public String getCommAddr() {
        return commAddr;
    }

    public void setCommAddr(String commAddr) {
        this.commAddr = commAddr;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getPayEeacc() {
        return payEeacc;
    }

    public void setPayEeacc(String payEeacc) {
        this.payEeacc = payEeacc;
    }

    public String getAccIdn() {
        return accIdn;
    }

    public void setAccIdn(String accIdn) {
        this.accIdn = accIdn;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccRel() {
        return accRel;
    }

    public void setAccRel(String accRel) {
        this.accRel = accRel;
    }

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
    }

    public String getGrdIdnNo() {
        return grdIdnNo;
    }

    public void setGrdIdnNo(String grdIdnNo) {
        this.grdIdnNo = grdIdnNo;
    }

    public String getGrdName() {
        return grdName;
    }

    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    public String getGrdBrDate() {
        return grdBrDate;
    }

    public void setGrdBrDate(String grdBrDate) {
        this.grdBrDate = grdBrDate;
    }

    public String getAssignIdnNo() {
        return assignIdnNo;
    }

    public void setAssignIdnNo(String assignIdnNo) {
        this.assignIdnNo = assignIdnNo;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
    }

    public String getAssignBrDate() {
        return assignBrDate;
    }

    public void setAssignBrDate(String assignBrDate) {
        this.assignBrDate = assignBrDate;
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

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getOnceAmt() {
        return onceAmt;
    }

    public void setOnceAmt(BigDecimal onceAmt) {
        this.onceAmt = onceAmt;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayYms() {
        return payYms;
    }

    public void setPayYms(String payYms) {
        this.payYms = payYms;
    }

    public String getPayYme() {
        return payYme;
    }

    public void setPayYme(String payYme) {
        this.payYme = payYme;
    }

    public String getPauseYm() {
        return pauseYm;
    }

    public void setPauseYm(String pauseYm) {
        this.pauseYm = pauseYm;
    }

    public String getUnPauseYm() {
        return unPauseYm;
    }

    public void setUnPauseYm(String unPauseYm) {
        this.unPauseYm = unPauseYm;
    }

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public String getLoanMk() {
        return loanMk;
    }

    public void setLoanMk(String loanMk) {
        this.loanMk = loanMk;
    }

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public String getRealexcLvl() {
        return realexcLvl;
    }

    public void setRealexcLvl(String realexcLvl) {
        this.realexcLvl = realexcLvl;
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

    public String getRechkDate() {
        return rechkDate;
    }

    public void setRechkDate(String rechkDate) {
        this.rechkDate = rechkDate;
    }

    public String getRechkMan() {
        return rechkMan;
    }

    public void setRechkMan(String rechkMan) {
        this.rechkMan = rechkMan;
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

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public String getArcPg() {
        return arcPg;
    }

    public void setArcPg(String arcPg) {
        this.arcPg = arcPg;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getPromoteUser() {
        return promoteUser;
    }

    public void setPromoteUser(String promoteUser) {
        this.promoteUser = promoteUser;
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

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getApNo1() {
        return apNo1;
    }

    public void setApNo1(String apNo1) {
        this.apNo1 = apNo1;
    }

    public String getApNo2() {
        return apNo2;
    }

    public void setApNo2(String apNo2) {
        this.apNo2 = apNo2;
    }

    public String getApNo3() {
        return apNo3;
    }

    public void setApNo3(String apNo3) {
        this.apNo3 = apNo3;
    }

    public String getApNo4() {
        return apNo4;
    }

    public void setApNo4(String apNo4) {
        this.apNo4 = apNo4;
    }

    public String getPayBankIdBranchId() {
        return payBankIdBranchId;
    }

    public void setPayBankIdBranchId(String payBankIdBranchId) {
        this.payBankIdBranchId = payBankIdBranchId;
    }

    public String getCvldtlName() {
        return cvldtlName;
    }

    public void setCvldtlName(String cvldtlName) {
        this.cvldtlName = cvldtlName;
    }

    public BigDecimal getOnceAvgAmt() {
        return onceAvgAmt;
    }

    public void setOnceAvgAmt(BigDecimal onceAvgAmt) {
        this.onceAvgAmt = onceAvgAmt;
    }

    public BigDecimal getOnceAplPayAmt() {
        return onceAplPayAmt;
    }

    public void setOnceAplPayAmt(BigDecimal onceAplPayAmt) {
        this.onceAplPayAmt = onceAplPayAmt;
    }

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
    }

    public BigDecimal getOnceOldAmt() {
        return onceOldAmt;
    }

    public void setOnceOldAmt(BigDecimal onceOldAmt) {
        this.onceOldAmt = onceOldAmt;
    }

    public BigDecimal getMustIssueAmt() {
        return mustIssueAmt;
    }

    public void setMustIssueAmt(BigDecimal mustIssueAmt) {
        this.mustIssueAmt = mustIssueAmt;
    }

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }

    public String getEvtExpireDate() {
        return evtExpireDate;
    }

    public void setEvtExpireDate(String evtExpireDate) {
        this.evtExpireDate = evtExpireDate;
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

    public BigDecimal getLsInsmAmt() {
        return lsInsmAmt;
    }

    public void setLsInsmAmt(BigDecimal lsInsmAmt) {
        this.lsInsmAmt = lsInsmAmt;
    }

    public String getLsChkMk() {
        return lsChkMk;
    }

    public void setLsChkMk(String lsChkMk) {
        this.lsChkMk = lsChkMk;
    }

    public BigDecimal getLsCount() {
        return lsCount;
    }

    public void setLsCount(BigDecimal lsCount) {
        this.lsCount = lsCount;
    }

    public String getLnChkMk() {
        return lnChkMk;
    }

    public void setLnChkMk(String lnChkMk) {
        this.lnChkMk = lnChkMk;
    }

    public String getDabApNo() {
        return dabApNo;
    }

    public void setDabApNo(String dabApNo) {
        this.dabApNo = dabApNo;
    }

    public BigDecimal getDabAnnuAmt() {
        return dabAnnuAmt;
    }

    public void setDabAnnuAmt(BigDecimal dabAnnuAmt) {
        this.dabAnnuAmt = dabAnnuAmt;
    }

    public String getEvtAppName() {
        return evtAppName;
    }

    public void setEvtAppName(String evtAppName) {
        this.evtAppName = evtAppName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

	public String getChkPayBankId() {
		return chkPayBankId;
	}

	public void setChkPayBankId(String chkPayBankId) {
		this.chkPayBankId = chkPayBankId;
	}

	public String getChkBranchId() {
		return chkBranchId;
	}

	public void setChkBranchId(String chkBranchId) {
		this.chkBranchId = chkBranchId;
	}

	public String getChkPayEeacc() {
		return chkPayEeacc;
	}

	public void setChkPayEeacc(String chkPayEeacc) {
		this.chkPayEeacc = chkPayEeacc;
	}

	public String getChkPayBankIdChkBranchId() {
		return chkPayBankIdChkBranchId;
	}

	public void setChkPayBankIdChkBranchId(String chkPayBankIdChkBranchId) {
		this.chkPayBankIdChkBranchId = chkPayBankIdChkBranchId;
	}

	public String getSpecialAcc() {
		return specialAcc;
	}

	public void setSpecialAcc(String specialAcc) {
		this.specialAcc = specialAcc;
	}
}
