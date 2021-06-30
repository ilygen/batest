package tw.gov.bli.ba.domain;

import java.math.BigDecimal;

/**
 * 核付報表總表會計科目紀錄檔 (<code>BAPAYRPTACCOUNT</code>)
 * 
 * @author Kiyomi
 */
public class Bapayrptaccount {
    private String rptTyp; // 清冊別
    private String cprnDate; // 清冊產生日期
    private BigDecimal rptPage; // 清冊頁次
    private BigDecimal eRptPage; // 清冊頁次
    private String payDate; // 核付日期
    private String chkDate; // 核定日期
    private String apNo; // 受理編號
    //private String seqNo; // 序號
    private BigDecimal seqNo;
    private String payCode; // 給付別
    private String payKind; // 給付種類
    private String issuTyp; // 核付分類
    private String caseTyp; // 案件類別
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String taTyp; // 出帳類別
    private String payTyp; // 給付方式
    private String benIds; // 受益人社福識別碼
    private String benIdn; // 受益人身分證號
    private String benName; // 受益人姓名
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payEeacc; // 銀行帳號
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal payAmt; // 實付金額
    private BigDecimal mitRate; // 匯款匯費
    private BigDecimal otheraAmt; // 另案扣減(同類保險)金額
    private BigDecimal otherbAmt; // 另案扣減(他類保險)金額
    private BigDecimal offsetAmt; // 抵銷紓困費用
    private BigDecimal compenAmt; // 代扣補償金
    private BigDecimal inherItorAmt; // 保留遺屬津貼
    private BigDecimal itrtTax; // 代扣利息所得稅
    private BigDecimal otherAmt; // 其他費用
    private String rptNote; // 備註
    private String dlineDate; // 勞貸本息截止日
    private String dlineSeqNo; // 流水號(勞貸)
    private String procTime; // 處理日期時間
    private String commZip; // 受益人聯絡郵遞區號
    private String commAddr; // 受益人聯絡地址
    private String commTel; // 受益人聯絡電話
    private String hjMk; // 移至註記
    private String otherApNo1; // 他類案件受理編號
    private String otherSeqNo1; // 他類案件受款人序號
    private String dataKd; // 資料種類
    private String benEvtRel; // 受益人與事故者關係
    private String accIdn; // 戶名 IDN
    private String accName; // 戶名
    private String compId1; // 相關單位 ID1
    private String compName1; // 相關單位名稱1
    private BigDecimal unacpAmt; // 應收金額
    private String compId2; // 相關單位 ID2
    private String compName2; // 相關單位名稱2
    
    private BigDecimal accountSeq;
    private String accountName;
    private String paySeqNo;
    private String nlWkMk;
    private String adWkMk;
    private String naChgMk;

    // Field not in BAPAYRPTACCOUNT
    // [

    // for MonthlyRpt04
    // [
    private BigDecimal apNoAmt;// 件數
    private BigDecimal dataAmt;// 筆數
    // ]

    // for MonthlyRpt 10
    // [
    private String accountNo; // 帳號
    private String payTypName; // 給付方式中文名稱
    // ]

    // for MonthlyRpt 08
    // [
    private String payAccount; // 金融機構轉帳帳號
    // ]

    // for MonthlyRpt 17
    private String dataTyp; // 資料種類
    private String bliPayeeAcc; // 本局帳戶

    // ]

    // for MonthlyRpt 12
    // [
    private String apNoLastDigit;// 受理編號的最後一碼

    // ]
    
    private String cPrnDate; //印表日期
    private String oppAccountNo; // 帳號
    private String oppAccountName;
    private String actTitleApCode;
    private String dataCd;
    private String acceptPayType;
    private String accountTopfMk;
    private BigDecimal cashAmt;
    private BigDecimal cutAmt;    
    private String accountMemo;
    private BigDecimal decideAmt;
    private BigDecimal cnt;
    
    private String sts;// 資料狀態
    private String actDb;
    private String actCr;
    private String apSeqNo;

    public String getApNoLastDigit() {
        return apNoLastDigit;
    }

    public void setApNoLastDigit(String apNoLastDigit) {
        this.apNoLastDigit = apNoLastDigit;
    }

    public String getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
    }

    public String getCprnDate() {
        return cprnDate;
    }

    public void setCprnDate(String cprnDate) {
        this.cprnDate = cprnDate;
    }

    public BigDecimal getRptPage() {
        return rptPage;
    }

    public void setRptPage(BigDecimal rptPage) {
        this.rptPage = rptPage;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    /**
    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    **/
    
    public void setSeqNo(BigDecimal seqNo) {
        this.seqNo = seqNo;
    }

    public BigDecimal getSeqNo() {
        return seqNo;
    }    

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getIssuTyp() {
        return issuTyp;
    }

    public void setIssuTyp(String issuTyp) {
        this.issuTyp = issuTyp;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
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

    public String getTaTyp() {
        return taTyp;
    }

    public void setTaTyp(String taTyp) {
        this.taTyp = taTyp;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
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

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
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

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
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

    public BigDecimal getOffsetAmt() {
        return offsetAmt;
    }

    public void setOffsetAmt(BigDecimal offsetAmt) {
        this.offsetAmt = offsetAmt;
    }

    public BigDecimal getCompenAmt() {
        return compenAmt;
    }

    public void setCompenAmt(BigDecimal compenAmt) {
        this.compenAmt = compenAmt;
    }

    public BigDecimal getInherItorAmt() {
        return inherItorAmt;
    }

    public void setInherItorAmt(BigDecimal inherItorAmt) {
        this.inherItorAmt = inherItorAmt;
    }

    public BigDecimal getItrtTax() {
        return itrtTax;
    }

    public void setItrtTax(BigDecimal itrtTax) {
        this.itrtTax = itrtTax;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public String getRptNote() {
        return rptNote;
    }

    public void setRptNote(String rptNote) {
        this.rptNote = rptNote;
    }

    public String getDlineDate() {
        return dlineDate;
    }

    public void setDlineDate(String dlineDate) {
        this.dlineDate = dlineDate;
    }

    public String getDlineSeqNo() {
        return dlineSeqNo;
    }

    public void setDlineSeqNo(String dlineSeqNo) {
        this.dlineSeqNo = dlineSeqNo;
    }

    public String getProcTime() {
        return procTime;
    }

    public void setProcTime(String procTime) {
        this.procTime = procTime;
    }

    public BigDecimal getApNoAmt() {
        return apNoAmt;
    }

    public void setApNoAmt(BigDecimal apNoAmt) {
        this.apNoAmt = apNoAmt;
    }

    public BigDecimal getDataAmt() {
        return dataAmt;
    }

    public void setDataAmt(BigDecimal dataAmt) {
        this.dataAmt = dataAmt;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPayTypName() {
        return payTypName;
    }

    public void setPayTypName(String payTypName) {
        this.payTypName = payTypName;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
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

    public String getCommTel() {
        return commTel;
    }

    public void setCommTel(String commTel) {
        this.commTel = commTel;
    }

    public String getHjMk() {
        return hjMk;
    }

    public void setHjMk(String hjMk) {
        this.hjMk = hjMk;
    }

    public String getOtherApNo1() {
        return otherApNo1;
    }

    public void setOtherApNo1(String otherApNo1) {
        this.otherApNo1 = otherApNo1;
    }

    public String getOtherSeqNo1() {
        return otherSeqNo1;
    }

    public void setOtherSeqNo1(String otherSeqNo1) {
        this.otherSeqNo1 = otherSeqNo1;
    }

    public String getDataKd() {
        return dataKd;
    }

    public void setDataKd(String dataKd) {
        this.dataKd = dataKd;
    }

    public String getDataTyp() {
        return dataTyp;
    }

    public void setDataTyp(String dataTyp) {
        this.dataTyp = dataTyp;
    }

    public String getBliPayeeAcc() {
        return bliPayeeAcc;
    }

    public void setBliPayeeAcc(String bliPayeeAcc) {
        this.bliPayeeAcc = bliPayeeAcc;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
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

    public String getCompId1() {
        return compId1;
    }

    public void setCompId1(String compId1) {
        this.compId1 = compId1;
    }

    public String getCompName1() {
        return compName1;
    }

    public void setCompName1(String compName1) {
        this.compName1 = compName1;
    }

    public BigDecimal getUnacpAmt() {
        return unacpAmt;
    }

    public void setUnacpAmt(BigDecimal unacpAmt) {
        this.unacpAmt = unacpAmt;
    }

    public String getCompName2() {
        return compName2;
    }

    public void setCompName2(String compName2) {
        this.compName2 = compName2;
    }

    public String getCompId2() {
        return compId2;
    }

    public void setCompId2(String compId2) {
        this.compId2 = compId2;
    }
    
    public BigDecimal getAccountSeq() {
        return accountSeq;
    }

    public void setAccountSeq(BigDecimal accountSeq) {
        this.accountSeq = accountSeq;
    }
    
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getPaySeqNo() {
        return paySeqNo;
    }

    public void setPaySeqNo(String paySeqNo) {
        this.paySeqNo = paySeqNo;
    }
    
    public String getNlWkMk() {
        return nlWkMk;
    }

    public void setNlWkMk(String nlWkMk) {
        this.nlWkMk = nlWkMk;
    }
    
    public String getAdWkMk() {
        return adWkMk;
    }

    public void setAdWkMk(String adWkMk) {
        this.adWkMk = adWkMk;
    }
    
    public String getNaChgMk() {
        return naChgMk;
    }

    public void setNaChgMk(String naChgMk) {
        this.naChgMk = naChgMk;
    }    
    
    public BigDecimal geteRptPage() {
		return eRptPage;
	}

	public void seteRptPage(BigDecimal eRptPage) {
		this.eRptPage = eRptPage;
	}
	
    public String getcPrnDate() {
        return cPrnDate;
    }

    public void setcPrnDate(String cPrnDate) {
        this.cPrnDate = cPrnDate;
    }
    
    public String getOppAccountNo() {
        return oppAccountNo;
    }

    public void setOppAccountNo(String oppAccountNo) {
        this.oppAccountNo = oppAccountNo;
    }
    
    public String getOppAccountName() {
        return oppAccountName;
    }

    public void setOppAccountName(String oppAccountName) {
        this.oppAccountName = oppAccountName;
    }
    
    public String getActTitleApCode() {
        return actTitleApCode;
    }

    public void setActTitleApCode(String actTitleApCode) {
        this.actTitleApCode = actTitleApCode;
    }
    
    public String getDataCd() {
        return dataCd;
    }

    public void setDataCd(String dataCd) {
        this.dataCd = dataCd;
    }    

    public String getAcceptPayType() {
        return acceptPayType;
    }

    public void setAcceptPayType(String acceptPayType) {
        this.acceptPayType = acceptPayType;
    }
    
    public String getAccountTopfMk() {
        return accountTopfMk;
    }

    public void setAccountTopfMk(String accountTopfMk) {
        this.accountTopfMk = accountTopfMk;
    }
    
    public BigDecimal getCashAmt() {
        return cashAmt;
    }
    public void setCashAmt(BigDecimal cashAmt) {
        this.cashAmt = cashAmt;
    }    
   
    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }    
    
    public String getAccountMemo() {
        return accountMemo;
    }

    public void setAccountMemo(String accountMemo) {
        this.accountMemo = accountMemo;
    }
    
    public BigDecimal getDecideAmt() {
        return decideAmt;
    }

    public void setDecideAmt(BigDecimal decideAmt) {
        this.decideAmt = decideAmt;
    }
    
    public BigDecimal getCnt() {
        return cnt;
    }

    public void setCnt(BigDecimal cnt) {
        this.cnt = cnt;
    }
    
    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
    
    public String getActDb() {
        return actDb;
    }

    public void setActDb(String actDb) {
        this.actDb = actDb;
    }
    
    public String getActCr() {
        return actCr;
    }

    public void setActCr(String actCr) {
        this.actCr = actCr;
    }
    
    public String getApSeqNo() {
        return apSeqNo;
    }

    public void setApSeqNo(String apSeqNo) {
        this.apSeqNo = apSeqNo;
    }

}
