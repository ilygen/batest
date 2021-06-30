package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 整批收回核定清單
 * 
 * @author Kiyomi
 */
public class BatchPaymentReceiveDataCase implements Serializable {
    private BigDecimal baunacpdtlId; // 資料列編號(應收帳務明細編號)
    private String insKd; // 保險別
    private String bli_Account_Code; // 局帳戶代號
    private String bookedBook; // 入帳方式
    private String bkAccountDt; // 銀行入帳日期
    private String batchNo; // 批號
    private String serialNo; // 流水號
    private BigDecimal cash_Amt; // 現金金額
    private String divSeq; // 分割序號
    private String index_No; // 序號
    private String moveToCode; // 移至註記
    private String tempHandleNo; // 受理編號
    private String affairrePrno; // 業務單位辦理收回承辦人員
    private String affairredept; // 業務單位辦理收回科別
    private String affairredate; // 業務單位辦理收回承辦日期
    private BigDecimal available_Money; // 可用餘額
    private BigDecimal affairreCount; // 業務單位辦理收回次數
    private String recm; // 應收月份
    private String camField; // 關鍵編號欄位

    // For CancelCashReceive
    private String insKdCash; // 保險別

    // BAACPDTL bli_Account_Code cashAmt
    private String bliAccountCode; // 局帳戶代號
    private BigDecimal cashAmt; // 現金金額
    private String indexNo; // 序號
    private BigDecimal recAmt; // 應收金額

    private Long retrieveMoney; // 收回金額

    // For BAUNACPDTL
    private String apNo;
    private String seqNo;
    private String issuYm;
    private String payYm;
    private BigDecimal recRem;
    private BigDecimal baacpdtlId;

    // For BAPAYRPTRECORD
    // private String apNo;
    // private String seqNo;
    private String payKind; // 給付種類
    // private String payYm;
    private String chkDate; // 核定日期
    private String issuTyp; // 核付分類
    private String benIds; // 受款人社福識別碼
    private String benIdnNo; // 受款人身份證號
    private String benName; // 受款人姓名
    private String payBankId; // 金融機構總代號
    private String branchId; // 金融機構分支代號
    private String payEeacc; // 銀行帳號
    private BigDecimal issueAmt; // 核定金額
    private String nlWkMk; // 普職註記
    private String adWkMk; // 加職註記
    private String naChgMk; // 普職互改註記
    private String caseTyp; // 案件類別

    // For BAPAYRPTSUM
    private BigDecimal apNoAmt;
    private BigDecimal dataAmt;
    // private BigDecimal issueAmt;
    private BigDecimal otheraAmt;
    private BigDecimal otherbAmt;
    private BigDecimal mitRate;
    private BigDecimal offsetAmt;
    private BigDecimal compenAmt;
    private BigDecimal inherItorAmt;
    private BigDecimal itrtTax;
    private BigDecimal otherAmt;
    // private String issuTyp;
    // private String chkDate;
    private String cPrnDate;
    private String rptTyp;
    private BigDecimal payAmt;

    // FOR BAPAYRPTACCOUNT
    private String accountNo; // 帳號
    private String accountName;
    private BigDecimal accountSeq;
    private String oppAccountNo; // 帳號
    private String oppAccountName;
    private String actTitleApCode;
    private String dataCd;
    private String acceptPayType;
    private String accountTopfMk;
    // private BigDecimal cashAmt;
    private BigDecimal cutAmt;
    private String accountMemo;
    private BigDecimal decideAmt;
    private BigDecimal cnt;

    // FOR 應收已收清冊
    // private String apNo;// 受理編號
    // private String payYm;// 給付年月
    // private String issuYm; //核定年月
    private String benIdn;// 受款人身份證號
    // private String benName;// 受款人姓名
    private String payTyp; // 給付方式
    // private BigDecimal issueAmt;// 核定金額
    // private BigDecimal otheraAmt;// 另案扣減(同類保險)金額
    // private BigDecimal otherbAmt;// 另案扣減(他類保險)金額
    // private BigDecimal mitRate;// 匯款匯費
    // private BigDecimal offsetAmt; // 抵銷紓困
    // private BigDecimal compenAmt; // 代扣補償金
    // private BigDecimal inherItorAmt; // 保留遺屬津貼
    // private BigDecimal itrtTax;// 代扣利息所得稅
    // private BigDecimal otherAmt;// 其他費用
    // private String issuTyp; // 案件類別
    private String payAccount;// 金融機構轉帳帳號

    // private String nlWkMk;
    // private String adWkMk;
    // private String naChgMk;
    private String isNaChgMk;

    private String apNoLastDigit;// 受理編號的最後一碼

    private BigDecimal rptPage; // 清冊頁次
    private BigDecimal eRptPage; // 清冊頁次
    // private String cPrnDate; //印表日期

    // private String payBankId; // 入帳銀行 (總行)
    // private String branchId; // 入帳銀行 (分行)
    // private String payEeacc; // 入帳帳號

    private String remark; // 備註
    private String sts;// 資料狀態
    private String payCode;
    private String crtUser;// 新增者代號
    private String crtTime;// 新增日期時間
    
    private String actDb;
    private String actCr;
    
    private BigDecimal issueAmtA; // 核定金額 A
    private BigDecimal issueAmtB; // 核定金額 B
    private BigDecimal payAmtA;   // 實付金額 A
    private BigDecimal payAmtB;   // 實付金額 B
    private String apSeqNo; // 序號
    
    public String getInsKd() {
        return insKd;
    }

    public void setInsKd(String insKd) {
        this.insKd = insKd;
    }

    public String getBli_Account_Code() {
        return bli_Account_Code;
    }

    public void setBli_Account_Code(String bli_Account_Code) {
        this.bli_Account_Code = bli_Account_Code;
    }

    public String getBookedBook() {
        return bookedBook;
    }

    public void setBookedBook(String bookedBook) {
        this.bookedBook = bookedBook;
    }

    public String getBkAccountDt() {
        return bkAccountDt;
    }

    public void setBkAccountDt(String bkAccountDt) {
        this.bkAccountDt = bkAccountDt;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getCash_Amt() {
        return cash_Amt;
    }

    public void setCash_Amt(BigDecimal cash_Amt) {
        this.cash_Amt = cash_Amt;
    }

    public String getDivSeq() {
        return divSeq;
    }

    public void setDivSeq(String divSeq) {
        this.divSeq = divSeq;
    }

    public String getIndex_No() {
        return index_No;
    }

    public void setIndex_No(String index_No) {
        this.index_No = index_No;
    }

    public String getMoveToCode() {
        return moveToCode;
    }

    public void setMoveToCode(String moveToCode) {
        this.moveToCode = moveToCode;
    }

    public String getTempHandleNo() {
        return tempHandleNo;
    }

    public void setTempHandleNo(String tempHandleNo) {
        this.tempHandleNo = tempHandleNo;
    }

    public String getAffairrePrno() {
        return affairrePrno;
    }

    public void setAffairrePrno(String affairrePrno) {
        this.affairrePrno = affairrePrno;
    }

    public String getAffairredept() {
        return affairredept;
    }

    public void setAffairredept(String affairredept) {
        this.affairredept = affairredept;
    }

    public String getAffairredate() {
        return affairredate;
    }

    public void setAffairredate(String affairredate) {
        this.affairredate = affairredate;
    }

    public BigDecimal getAvailable_Money() {
        return available_Money;
    }

    public void setAvailable_Money(BigDecimal available_Money) {
        this.available_Money = available_Money;
    }

    public BigDecimal getAffairreCount() {
        return affairreCount;
    }

    public void setAffairreCount(BigDecimal affairreCount) {
        this.affairreCount = affairreCount;
    }

    public String getRecm() {
        return recm;
    }

    public void setRecm(String recm) {
        this.recm = recm;
    }

    public String getCamField() {
        return camField;
    }

    public void setCamField(String camField) {
        this.camField = camField;
    }

    public Long getRetrieveMoney() {
        return retrieveMoney;
    }

    public void setRetrieveMoney(Long retrieveMoney) {
        this.retrieveMoney = retrieveMoney;
    }

    public String getInsKdCash() {
        return insKdCash;
    }

    public void setInsKdCash(String insKdCash) {
        this.insKdCash = insKdCash;
    }

    public String getBliAccountCode() {
        return bliAccountCode;
    }

    public void setBliAccountCode(String bliAccountCode) {
        this.bliAccountCode = bliAccountCode;
    }

    public BigDecimal getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(BigDecimal cashAmt) {
        this.cashAmt = cashAmt;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public BigDecimal getRecAmt() {
        return recAmt;
    }

    public void setRecAmt(BigDecimal recAmt) {
        this.recAmt = recAmt;
    }

    public BigDecimal getBaunacpdtlId() {
        return baunacpdtlId;
    }

    public void setBaunacpdtlId(BigDecimal baunacpdtlId) {
        this.baunacpdtlId = baunacpdtlId;
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

    public BigDecimal getRecRem() {
        return recRem;
    }

    public void setRecRem(BigDecimal recRem) {
        this.recRem = recRem;
    }

    public BigDecimal getBaacpdtlId() {
        return baacpdtlId;
    }

    public void setBaacpdtlId(BigDecimal baacpdtlId) {
        this.baacpdtlId = baacpdtlId;
    }

    // For BAPAYRPTRECORD
    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getIssuTyp() {
        return issuTyp;
    }

    public void setIssuTyp(String issuTyp) {
        this.issuTyp = issuTyp;
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

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    // For BAPAYRPTSUM
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

    public BigDecimal getMitRate() {
        return mitRate;
    }

    public void setMitRate(BigDecimal mitRate) {
        this.mitRate = mitRate;
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

    public String getcPrnDate() {
        return cPrnDate;
    }

    public void setcPrnDate(String cPrnDate) {
        this.cPrnDate = cPrnDate;
    }

    public String getRptTyp() {
        return rptTyp;
    }

    public void setRptTyp(String rptTyp) {
        this.rptTyp = rptTyp;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    // FOR BAPAYRPTACCOUNT
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getAccountSeq() {
        return accountSeq;
    }

    public void setAccountSeq(BigDecimal accountSeq) {
        this.accountSeq = accountSeq;
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

    // FOR 應收已收清冊
    public String getBenIdn() {
        return benIdn;
    }

    public void setBenIdn(String benIdn) {
        this.benIdn = benIdn;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public String getIsNaChgMk() {
        return isNaChgMk;
    }

    public void setIsNaChgMk(String isNaChgMk) {
        this.isNaChgMk = isNaChgMk;
    }

    public String getApNoLastDigit() {
        return apNoLastDigit;
    }

    public void setApNoLastDigit(String apNoLastDigit) {
        this.apNoLastDigit = apNoLastDigit;
    }

    public BigDecimal getRptPage() {
        return rptPage;
    }

    public void setRptPage(BigDecimal rptPage) {
        this.rptPage = rptPage;
    }

    public BigDecimal geteRptPage() {
        return eRptPage;
    }

    public void seteRptPage(BigDecimal eRptPage) {
        this.eRptPage = eRptPage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
    
    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
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
    
    public BigDecimal getIssueAmtA() {
        return issueAmtA;
    }

    public void setIssueAmtA(BigDecimal issueAmtA) {
        this.issueAmtA = issueAmtA;
    }

    public BigDecimal getIssueAmtB() {
        return issueAmtB;
    }

    public void setIssueAmtB(BigDecimal issueAmtB) {
        this.issueAmtB = issueAmtB;
    }

    public BigDecimal getPayAmtA() {
        return payAmtA;
    }

    public void setPayAmtA(BigDecimal payAmtA) {
        this.payAmtA = payAmtA;
    }
    
    public BigDecimal getPayAmtB() {
        return payAmtB;
    }

    public void setPayAmtB(BigDecimal payAmtB) {
        this.payAmtB = payAmtB;
    }
    
    public String getApSeqNo() {
        return apSeqNo;
    }

    public void setApSeqNo(String apSeqNo) {
        this.apSeqNo = apSeqNo;
    }


    public String getApNoStr() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    public String getPayCodeStr(String adWkMk, String nlWkMk, String isNaChgMk) {
        String payCodeStr = "";
        String isNaChgMkStr = "";
        String paySeqStr = "";

        if ("Y".equals(isNaChgMk)) {
            if ("12".equals(naChgMk)) {
                isNaChgMkStr = " - 普改職";
            }
            else if ("13".equals(naChgMk)) {
                isNaChgMkStr = " - 普改加職";
            }
            else if ("21".equals(naChgMk)) {
                isNaChgMkStr = " - 職改普";
            }
            else if ("23".equals(naChgMk)) {
                isNaChgMkStr = " - 職改加職";
            }
            else if ("31".equals(naChgMk)) {
                isNaChgMkStr = " - 加職改普";
            }
            else if ("32".equals(naChgMk)) {
                isNaChgMkStr = " - 加職改職";
            }
        }
        else if ("N".equals(isNaChgMk)) {
            isNaChgMkStr = "";
        }

        if (apNo.equals("") || apNo.equals(null)) {
            return "";
        }
        else {
            if (apNo.substring(0, 1).equals("L"))
                return "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L + "(年金)";
            else if (apNo.substring(0, 1).equals("K"))
                if (("1").equals(nlWkMk)) {
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    return payCodeStr = "職災-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    return payCodeStr = "加職-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)" + isNaChgMkStr;
                }
                else {
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K + "(年金)";
                }
            // return "○○"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K+"(年金)";
            else if (apNo.substring(0, 1).equals("S"))
                if (("1").equals(nlWkMk)) {
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("1").equals(adWkMk)) {
                    return payCodeStr = "職災-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)" + isNaChgMkStr;
                }
                else if (("2").equals(nlWkMk) && ("2").equals(adWkMk)) {
                    return payCodeStr = "加職-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)" + isNaChgMkStr;
                }
                else {
                    return payCodeStr = "普通-" + ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S + "(年金)";
                }
            // return "○○"+ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S+"(年金)";
            else
                return "";
        }
    }

    // 核付分類
    public String getMchkTypStr() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else if ((ConstantKey.BAAPPBASE_CASETYP_E).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_E;
        else if ((ConstantKey.BAAPPBASE_CASETYP_F).equals(getIssuTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_F;
        else
            return "";
    }

    public String getIssuTypStr() {
        if (issuTyp.equals("1"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if (issuTyp.equals("2"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if (issuTyp.equals("5"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if (issuTyp.equals("F"))
            return ConstantKey.BAAPPBASE_CASETYP_STR_F;
        else
            return "";
    }

    public String getDeptNameString() {
        String payKindCode = StringUtils.substring(apNo, 0, 1);

        if (StringUtils.equalsIgnoreCase(payKindCode, "L"))
            return "生老";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "K"))
            return "傷殘";
        else if (StringUtils.equalsIgnoreCase(payKindCode, "S"))
            return "死亡";
        else
            return "";
    }

    public String getPayBankIdStr() {
        String payBankIdStr = "";

        payBankId = (StringUtils.isNotBlank(payBankId) ? payBankId : "");
        branchId = (StringUtils.isNotBlank(branchId) ? branchId : "");
        payEeacc = (StringUtils.isNotBlank(payEeacc) ? payEeacc : "");

        if (payTyp.equals("1") && !payBankId.equals("700")) {
            if (StringUtils.isBlank(payBankId) && StringUtils.isBlank(payEeacc)) {
                payBankIdStr = "";
            }
            else {
                payBankIdStr = payBankId + "-0000-" + payEeacc;
            }
        }
        else {
            payBankIdStr = payBankId + "-" + branchId + "-" + payEeacc;
        }
        return payBankIdStr;
    }

    /**
     * 印表日期
     * 
     * @return
     */
    public String getcPrnDateStr() {
        if (StringUtils.isNotBlank(getcPrnDate()))
            return DateUtility.changeDateType(getcPrnDate());
        else
            return "";
    }

    public String getChkDateStr() {
        if (StringUtils.isNotBlank(getChkDate()))
            return DateUtility.changeDateType(getChkDate());
        else
            return "";
    }

    public String getPayTypStr() {
        if (payTyp.equals("1"))
            return "金融轉帳";
        else if (payTyp.equals("2"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_2;
        else if (payTyp.equals("3"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_3;
        else if (payTyp.equals("4"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_4;
        else if (payTyp.equals("5"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_5;
        else if (payTyp.equals("6"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_6;
        else if (payTyp.equals("7"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_7;
        else if (payTyp.equals("8"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_8;
        else if (payTyp.equals("9"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_9;
        else if (payTyp.equals("A"))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_A;
        else
            return "";
    }

}
