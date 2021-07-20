package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 給付查詢作業 受款人資料
 * 
 * @author Rickychi
 */
public class PaymentQueryBenDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 受款人序
    private String appDate; // 申請日期
    private String benIdnNo; // 受款人身分證號
    private String benName; // 受款人姓名
    private String benBrDate; // 受款人出生日期
    private String benDieDate;// 受益人死亡日期
    private String benSex; // 性別
    private String benNationTyp; // 國籍別
    private String benNationCode; // 國籍
    private String benNationName; // 國籍名稱
    private String benEvtRel; // 關係
    private String idnChkYm; // 身分查核年月
    private String tel1; // 電話1
    private String tel2; // 電話2
    private String commTyp; // 通訊地址別
    private String commZip; // 通訊郵遞區號
    private String commAddr; // 通訊地址
    private String payTyp; // 給付方式
    private String bankName; // 金融機構名稱
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payEeacc; // 銀行帳號
    private String accName; // 戶名
    private String accRel; // 戶名與受益人關係
    private String grdIdnNo; // 法定代理人身分證號
    private String grdName; // 法定代理人姓名
    private String grdBrDate; // 法定代理人出生日期
    private String benEvtAppDate; // 繼承人申請日期
    private String benEvtName; // 繼承人自受款人
    private String benMarrMk;// 婚姻狀況
    private String closeDate;// 結案日期
    private String closeCause;// 結案原因
    private String choiceYm; // 擇領起月

    private String issuPayYm;// 編審核定年月
    private Integer chkFileDataSize;// 資料筆數
    private String oldAplDpt;// 申請代算單位
    private BigDecimal cutAmt;// 扣減/補償總金額
    private String uname;// 投保單位名稱
    private List<PaymentQueryBenExtDataCase> benIssuDataList;// 核定資料
    private List<PaymentQueryBenExtDataCase> benChkDataList;// 編審註記資料

    // for 遺屬年金給付查詢[
    private List<PaymentQueryFamilyDataCase> benChkList;// 眷屬編審註記資料
    private List<PaymentQueryFamilyDataCase> matchChkList;// 符合註記資料
    private String unqualifiedCause; // 不合格原因

    // 遺屬欄位資料
    private String rehcMk; // 重新查核失能程度註記
    private String rehcYm; // 重新查核失能程度年月
    private String monIncomeMk; // 每月工作收入註記
    private BigDecimal monIncome; // 每月工作收入
    private String studMk; // 在學註記
    private String marryDate; // 結婚日期
    private String digamyDate; // 再婚日期
    private String abanApsYm; // 放棄請領起始年月
    private String raiseChildMk; // 配偶扶養
    private String handIcapMk; // 領有重度以上身心障礙手冊或證明註記
    private String interDictMk; // 受禁治產(監護)宣告
    private String interDictDate;// 受禁治產(監護)宣告 - 宣告日期
    private String repealInterdictDate;// 受禁治產(監護)宣告 - 撤銷日期
    private String benMissingSdate; // 遺屬失蹤期間(起)
    private String benMissingEdate; // 遺屬失蹤期間(迄)
    private String prisonSdate; // 監管期間(起)
    private String prisonEdate; // 監管期間(迄)
    private String relatChgDate; // 親屬關係變動日期
    private String adoPtDate; // 收養日期
    private String judgeDate; // 判決日期
    private String raiseEvtMk; // 被保險人扶養
    private String assignIdnNo; // 代辦人身分證號
    private String assignName; // 代辦人姓名
    private String assignBrDate; // 代辦人出生日期
    private String ableApsYm;// 得請領起始年月
    private String savingMk; // 計息存儲
    // ]

    // 在學起迄年月
    private String studSdate;// 在學起始年月
    private String studEdate;// 在學結束年月
    private BigDecimal studDataCount;// 在學記錄筆數

    // 重殘起迄年月
    private String handicapSdate;// 重殘起始年月
    private String handicapEdate;// 重殘結束年月
    private BigDecimal handicapDataCount;// 重殘記錄筆數
    
    // 強迫不合格起迄年月
    private String compelSdate; // 強迫不合格起始年月
    private String compelEdate; // 強迫不合格結束年月
    private BigDecimal dataCount; // 強迫不合格記錄筆數
    private String compelDesc; // 強迫不合格原因

    private String specialAcc;// 專戶
    private String schoolCodeStr;// 學校代碼 中文
    
    //Added By LouisChange 20200311 begin
    private String rmp_Name; // 原住民羅馬拼音
    //Added By LouisChange 20200311 end

    // 頁面顯示轉換
    // [
    public String getBenMarrMkStr() {
        String benMarrMkStr = getBenMarrMk();
        if (("Y").equals(getBenMarrMk())) {
            benMarrMkStr = "已婚";
        }
        else if (("N").equals(getBenMarrMk())) {
            benMarrMkStr = "未婚";
        }
        return benMarrMkStr;
    }

    public String getBenEvtRelStr() {
        String benEvtRelStr = "";
        if (("1").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        }
        else if (("2").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        }
        else if (("3").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        }
        else if (("4").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        }
        else if (("5").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        }
        else if (("6").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        }
        else if (("7").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        }
        else if (("A").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        }
        else if (("C").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        }
        else if (("E").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        }
        else if (("F").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        }
        else if (("N").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        }
        else if (("Z").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        }
        else if (("O").equals(getBenEvtRel())) {
            benEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        }
        return benEvtRelStr;
    }

    public String getPayTypStr() {
        String payTypStr = "";
        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_1;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_2;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_3;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_4).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_4;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_5;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_6).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_6;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_7).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_7;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_8).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_8;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_9).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_9;
        }
        else if ((ConstantKey.BAAPPBASE_PAYTYP_A).equals(getPayTyp())) {
            payTypStr = ConstantKey.BAAPPBASE_PAYTYP_STR_A;
        }
        return payTypStr;
    }

    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return getAppDate();
        }
    }

    public String getBenBrDateStr() {
        if (StringUtils.isNotBlank(getBenBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getBenBrDate());
        }
        else {
            return getBenBrDate();
        }
    }

    public String getIdnChkYmStr() {
        if (StringUtils.isNotBlank(getIdnChkYm())) {
            return DateUtility.changeWestYearMonthType(getIdnChkYm());
        }
        else {
            return getIdnChkYm();
        }
    }

    public String getGrdBrDateStr() {
        if (StringUtils.isNotBlank(getGrdBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getGrdBrDate());
        }
        else {
            return getGrdBrDate();
        }
    }

    public String getBenEvtAppDateStr() {
        if (StringUtils.isNotBlank(getBenEvtAppDate())) {
            return DateUtility.changeDateType(getBenEvtAppDate());
        }
        else {
            return getBenEvtAppDate();
        }
    }

    public String getMarryDateStr() {
        if (StringUtils.isNotBlank(getMarryDate())) {
            return DateUtility.changeDateType(getMarryDate());
        }
        else {
            return getMarryDate();
        }
    }

    public String getDigamyDateStr() {
        if (StringUtils.isNotBlank(getDigamyDate())) {
            return DateUtility.changeDateType(getDigamyDate());
        }
        else {
            return getDigamyDate();
        }
    }

    public String getAbanApsYmStr() {
        if (StringUtils.isNotBlank(getAbanApsYm())) {
            return DateUtility.changeWestYearMonthType(getAbanApsYm());
        }
        else {
            return getAbanApsYm();
        }
    }

    public String getRaiseChildMkStr() {
        String raiseChildMkStr = "";
        if (StringUtils.isNotBlank(getRaiseChildMk()) && ("Y").equals(getRaiseChildMk())) {
            raiseChildMkStr = "有";
        }
        return raiseChildMkStr;
    }

    public String getHandIcapMkStr() {
        String handIcapMkStr = "";
        if (StringUtils.isNotBlank(getHandIcapMk()) && ("Y").equals(getHandIcapMk())) {
            handIcapMkStr = "有";
        }
        return handIcapMkStr;
    }

    public String getMonIncomeMkStr() {
        String monIncomeMkStr = "";
        if (StringUtils.isNotBlank(getMonIncomeMk()) && ("Y").equals(getMonIncomeMk())) {
            monIncomeMkStr = "有";
        }
        return monIncomeMkStr;
    }

    public String getBenMissingSdateStr() {
        if (StringUtils.isNotBlank(getBenMissingSdate())) {
            return DateUtility.changeDateType(getBenMissingSdate());
        }
        else {
            return getBenMissingSdate();
        }
    }

    public String getBenMissingEdateStr() {
        if (StringUtils.isNotBlank(getBenMissingEdate())) {
            return DateUtility.changeDateType(getBenMissingEdate());
        }
        else {
            return getBenMissingEdate();
        }
    }

    public String getPrisonSdateStr() {
        if (StringUtils.isNotBlank(getPrisonSdate())) {
            return DateUtility.changeDateType(getPrisonSdate());
        }
        else {
            return getPrisonSdate();
        }
    }

    public String getPrisonEdateStr() {
        if (StringUtils.isNotBlank(getPrisonEdate())) {
            return DateUtility.changeDateType(getPrisonEdate());
        }
        else {
            return getPrisonEdate();
        }
    }

    public String getRelatChgDateStr() {
        if (StringUtils.isNotBlank(getRelatChgDate())) {
            return DateUtility.changeDateType(getRelatChgDate());
        }
        else {
            return getRelatChgDate();
        }
    }

    public String getAdoPtDateStr() {
        if (StringUtils.isNotBlank(getAdoPtDate())) {
            return DateUtility.changeDateType(getAdoPtDate());
        }
        else {
            return getAdoPtDate();
        }
    }

    public String getJudgeDateStr() {
        if (StringUtils.isNotBlank(getJudgeDate())) {
            return DateUtility.changeDateType(getJudgeDate());
        }
        else {
            return getJudgeDate();
        }
    }

    public String getRaiseEvtMkStr() {
        String raiseEvtMkStr = "";
        if (StringUtils.isNotBlank(getRaiseEvtMk()) && ("Y").equals(getRaiseEvtMk())) {
            raiseEvtMkStr = "有";
        }
        return raiseEvtMkStr;
    }

    public String getAssignBrDateStr() {
        if (StringUtils.isNotBlank(getAssignBrDate())) {
            return PresentationUtility.changeDateTypeForDisplay(getAssignBrDate());
        }
        else {
            return getAssignBrDate();
        }
    }

    public String getStudSdateStr() {
        if (StringUtils.isNotBlank(getStudSdate())) {
            return DateUtility.changeWestYearMonthType(getStudSdate());
        }
        else {
            return getStudSdate();
        }
    }

    public String getStudEdateStr() {
        if (StringUtils.isNotBlank(getStudEdate())) {
            return DateUtility.changeWestYearMonthType(getStudEdate());
        }
        else {
            return getStudEdate();
        }
    }

    public String getBenDieDateStr() {
        if (StringUtils.isNotBlank(getBenDieDate())) {
            return DateUtility.changeDateType(getBenDieDate());
        }
        else {
            return getBenDieDate();
        }
    }

    public String getCloseDateStr() {
        if (StringUtils.isNotBlank(getCloseDate())) {
            return DateUtility.changeDateType(getCloseDate());
        }
        else {
            return getCloseDate();
        }
    }

    public String getChoiceYmStr() {
        if (StringUtils.isNotBlank(getChoiceYm())) {
            return DateUtility.changeWestYearMonthType(getChoiceYm());
        }
        else {
            return getChoiceYm();
        }
    }

    public String getInterDictDateStr() {
        if (StringUtils.isNotBlank(getInterDictDate())) {
            return DateUtility.changeDateType(getInterDictDate());
        }
        else {
            return getInterDictDate();
        }
    }

    public String getRepealInterdictDateStr() {
        if (StringUtils.isNotBlank(getRepealInterdictDate())) {
            return DateUtility.changeDateType(getRepealInterdictDate());
        }
        else {
            return getRepealInterdictDate();
        }
    }

    public String getAbleApsYmStr() {
        if (StringUtils.isNotBlank(getAbleApsYm())) {
            return DateUtility.changeWestYearMonthType(getAbleApsYm());
        }
        else {
            return getAbleApsYm();
        }
    }

    public String getSavingMkStr() {
        String savingMkStr = getSavingMk();
        if (("Y").equals(getSavingMk())) {
            savingMkStr = ConstantKey.BAAPPEXPAND_SAVINGMK_STR_Y;
        }
        else if (("T").equals(getSavingMk())) {
            savingMkStr = ConstantKey.BAAPPEXPAND_SAVINGMK_STR_T;
        }
        else {
            savingMkStr = "";
        }
        return savingMkStr;
    }

    // ]

    public String getBaappbaseId() {
        return baappbaseId;
    }

    public void setBaappbaseId(String baappbaseId) {
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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
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

    public String getBenNationName() {
        return benNationName;
    }

    public void setBenNationName(String benNationName) {
        this.benNationName = benNationName;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
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

    public String getBenEvtAppDate() {
        return benEvtAppDate;
    }

    public void setBenEvtAppDate(String benEvtAppDate) {
        this.benEvtAppDate = benEvtAppDate;
    }

    public String getBenEvtName() {
        return benEvtName;
    }

    public void setBenEvtName(String benEvtName) {
        this.benEvtName = benEvtName;
    }

    public List<PaymentQueryBenExtDataCase> getBenIssuDataList() {
        return benIssuDataList;
    }

    public void setBenIssuDataList(List<PaymentQueryBenExtDataCase> benIssuDataList) {
        this.benIssuDataList = benIssuDataList;
    }

    public List<PaymentQueryBenExtDataCase> getBenChkDataList() {
        return benChkDataList;
    }

    public void setBenChkDataList(List<PaymentQueryBenExtDataCase> benChkDataList) {
        this.benChkDataList = benChkDataList;
    }

    public String getIssuPayYm() {
        return issuPayYm;
    }

    public void setIssuPayYm(String issuPayYm) {
        this.issuPayYm = issuPayYm;
    }

    public Integer getChkFileDataSize() {
        return chkFileDataSize;
    }

    public void setChkFileDataSize(Integer chkFileDataSize) {
        this.chkFileDataSize = chkFileDataSize;
    }

    public String getOldAplDpt() {
        return oldAplDpt;
    }

    public void setOldAplDpt(String oldAplDpt) {
        this.oldAplDpt = oldAplDpt;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getBenMarrMk() {
        return benMarrMk;
    }

    public void setBenMarrMk(String benMarrMk) {
        this.benMarrMk = benMarrMk;
    }

    public List<PaymentQueryFamilyDataCase> getBenChkList() {
        return benChkList;
    }

    public void setBenChkList(List<PaymentQueryFamilyDataCase> benChkList) {
        this.benChkList = benChkList;
    }

    public List<PaymentQueryFamilyDataCase> getMatchChkList() {
        return matchChkList;
    }

    public void setMatchChkList(List<PaymentQueryFamilyDataCase> matchChkList) {
        this.matchChkList = matchChkList;
    }

    public String getRehcMk() {
        return rehcMk;
    }

    public void setRehcMk(String rehcMk) {
        this.rehcMk = rehcMk;
    }

    public String getRehcYm() {
        return rehcYm;
    }

    public void setRehcYm(String rehcYm) {
        this.rehcYm = rehcYm;
    }

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public BigDecimal getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(BigDecimal monIncome) {
        this.monIncome = monIncome;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
    }

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getDigamyDate() {
        return digamyDate;
    }

    public void setDigamyDate(String digamyDate) {
        this.digamyDate = digamyDate;
    }

    public String getAbanApsYm() {
        return abanApsYm;
    }

    public void setAbanApsYm(String abanApsYm) {
        this.abanApsYm = abanApsYm;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public String getInterDictDate() {
        return interDictDate;
    }

    public void setInterDictDate(String interDictDate) {
        this.interDictDate = interDictDate;
    }

    public String getBenMissingSdate() {
        return benMissingSdate;
    }

    public void setBenMissingSdate(String benMissingSdate) {
        this.benMissingSdate = benMissingSdate;
    }

    public String getBenMissingEdate() {
        return benMissingEdate;
    }

    public void setBenMissingEdate(String benMissingEdate) {
        this.benMissingEdate = benMissingEdate;
    }

    public String getPrisonSdate() {
        return prisonSdate;
    }

    public void setPrisonSdate(String prisonSdate) {
        this.prisonSdate = prisonSdate;
    }

    public String getPrisonEdate() {
        return prisonEdate;
    }

    public void setPrisonEdate(String prisonEdate) {
        this.prisonEdate = prisonEdate;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
    }

    public String getAdoPtDate() {
        return adoPtDate;
    }

    public void setAdoPtDate(String adoPtDate) {
        this.adoPtDate = adoPtDate;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getRaiseEvtMk() {
        return raiseEvtMk;
    }

    public void setRaiseEvtMk(String raiseEvtMk) {
        this.raiseEvtMk = raiseEvtMk;
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

    public String getStudSdate() {
        return studSdate;
    }

    public void setStudSdate(String studSdate) {
        this.studSdate = studSdate;
    }

    public String getStudEdate() {
        return studEdate;
    }

    public void setStudEdate(String studEdate) {
        this.studEdate = studEdate;
    }

    public BigDecimal getStudDataCount() {
        return studDataCount;
    }

    public void setStudDataCount(BigDecimal studDataCount) {
        this.studDataCount = studDataCount;
    }

    public String getBenDieDate() {
        return benDieDate;
    }

    public void setBenDieDate(String benDieDate) {
        this.benDieDate = benDieDate;
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

    public String getChoiceYm() {
        return choiceYm;
    }

    public void setChoiceYm(String choiceYm) {
        this.choiceYm = choiceYm;
    }

    public String getAbleApsYm() {
        return ableApsYm;
    }

    public void setAbleApsYm(String ableApsYm) {
        this.ableApsYm = ableApsYm;
    }

    public String getRepealInterdictDate() {
        return repealInterdictDate;
    }

    public void setRepealInterdictDate(String repealInterdictDate) {
        this.repealInterdictDate = repealInterdictDate;
    }

    public String getSavingMk() {
        return savingMk;
    }

    public void setSavingMk(String savingMk) {
        this.savingMk = savingMk;
    }

    public String getCompelSdate() {
        return compelSdate;
    }

    public void setCompelSdate(String compelSdate) {
        this.compelSdate = compelSdate;
    }

    public String getCompelEdate() {
        return compelEdate;
    }

    public void setCompelEdate(String compelEdate) {
        this.compelEdate = compelEdate;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public String getCompelDesc() {
        return compelDesc;
    }

    public void setCompelDesc(String compelDesc) {
        this.compelDesc = compelDesc;
    }

    public String getCompelSdateStr() {
        if (StringUtils.isNotBlank(getCompelSdate())) {
            return DateUtility.changeWestYearMonthType(getCompelSdate());
        }
        else {
            return getCompelSdate();
        }
    }

    public String getCompelEdateStr() {
        if (StringUtils.isNotBlank(getCompelEdate())) {
            return DateUtility.changeWestYearMonthType(getCompelEdate());
        }
        else {
            return getCompelEdate();
        }
    }

    public String getSpecialAcc() {
        return specialAcc;
    }

    public void setSpecialAcc(String specialAcc) {
        this.specialAcc = specialAcc;
    }

    public String getSchoolCodeStr() {
        return schoolCodeStr;
    }

    public void setSchoolCodeStr(String schoolCodeStr) {
        this.schoolCodeStr = schoolCodeStr;
    }

    public String getUnqualifiedCause() {
        return unqualifiedCause;
    }

    public void setUnqualifiedCause(String unqualifiedCause) {
        this.unqualifiedCause = unqualifiedCause;
    }

    public String getHandicapSdate() {
        return handicapSdate;
    }

    public void setHandicapSdate(String handicapSdate) {
        this.handicapSdate = handicapSdate;
    }

    public String getHandicapEdate() {
        return handicapEdate;
    }

    public void setHandicapEdate(String handicapEdate) {
        this.handicapEdate = handicapEdate;
    }

    public BigDecimal getHandicapDataCount() {
        return handicapDataCount;
    }

    public void setHandicapDataCount(BigDecimal handicapDataCount) {
        this.handicapDataCount = handicapDataCount;
    }

    public String getHandicapSdateStr() {
        if (StringUtils.isNotBlank(getHandicapSdate())) {
            return DateUtility.changeWestYearMonthType(getHandicapSdate());
        }
        else {
            return getHandicapSdate();
        }
    }
    
    public String getHandicapEdateStr() {
        if (StringUtils.isNotBlank(getHandicapEdate())) {
            return DateUtility.changeWestYearMonthType(getHandicapEdate());
        }
        else {
            return getHandicapEdate();
        }
    }

    //Added By LouisChange 20200311 begin
    public String getRmp_Name() {
		return rmp_Name;
	}

	public void setRmp_Name(String rmp_Name) {
		this.rmp_Name = rmp_Name;
	}
	//Added By LouisChange 20200311 end
	
}
