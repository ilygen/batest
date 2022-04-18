package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.LogField;

/**
 * Case for 勞保老年年金給付受理編審清單
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01Case implements Serializable {
    // 事故者資料
    // [
    private BigDecimal baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String imk; // 保險別
    private String apItem; // 申請項目
    private String payKind; // 給付種類
    private String appDate; // 申請日期
    private String caseTyp; // 案件類別
    private String mapNo; // 相關受理編號
    private String mapRootMk; // 相關受理編號註記
    private String combapMark; // 國勞合併申請註記
    private String caseMk; // 案件註記
    private String procStat; // 處理狀態
    private String acceptMk; // 合格註記
    private String manchkMk; // 人工審核結果
    private String chgNote; // 更正原因
    private String exeStat; // 決行狀況
    private String apUbno; // 申請單位保險證號
    private String apubnock; // 申請單位保險證號檢查碼
    private String lsUbno; // 最後單位保險證號
    private String lsUbnoCk; // 最後單位保險證號檢查碼
    private String evtIds; // 事故者社福識別碼
    private String evtIdnNo; // 事故者身分證號
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    private String evtSex; // 事故者性別
    private String evtNationTpe; // 事故者國籍別
    private String evtNationCode; // 事故者國籍
    private String evtIdent; // 事故者身分別
    private String evtJobDate; // 事故者離職日期
    private String evtDate; // 事故日期
    private String evtDieDate; // 事故者死亡日期
    private String evtAge; // 事故者申請時年齡
    private String benIds; // 受益人社福識別碼
    private String benIdnNo; // 受益人身分證號
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
    private String benSex; // 受益人性別
    private String benNationTyp; // 受益人國籍別
    private String benNationCode; // 受益人國籍(代碼)
    private String benNationName;// 受益人國籍(中文)
    private String benEvtRel; // 受益人與事故者關係
    private String benDieDate; // 受益人死亡日期
    private String benPayMk; // 受益人領取狀態註記
    private String idnChkYm; // 身分查核年月
    private String idnChkNote; // 身分查核註記
    private String tel1; // 電話 1
    private String tel2; // 電話 2
    private String commTyp; // 通訊地址別
    private String commZip; // 通訊郵遞區號
    private String commAddr; // 通訊地址
    private String payTyp; // 給付方式
    private String bankName; // 金融機構名稱
    private String payBankId; // 金融機構總代號
    private String branchId; // 分支代號
    private String payEeacc; // 銀行帳號
    private String accIdn; // 戶名 IDN
    private String accName; // 戶名
    private String accRel; // 戶名與受益人關係
    private BigDecimal mitRate; // 匯款匯費
    private String grdIdnNo; // 法定代理人身分證號
    private String grdName; // 法定代理人姓名
    private String grdBrDate; // 法定代理人出生日期
    private String assignIdnNo; // 受委託人身分證號
    private String assignName; // 受委託人姓名
    private String assignBrDate; // 受委託人出生日期
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private BigDecimal issueAmt; // 核定金額
    private BigDecimal onceAmt; // 一次給付核定金額
    private BigDecimal onceAvgAmt; // 一次給付平均薪資
    private BigDecimal onceAplPayAmt; // 一次給付實發年資
    private String oncePayMk; // 一次給付符合註記
    private BigDecimal onceOldAmt; // 老年一次金金額
    private String oldSeniab; // 一次請領之年資採計方式
    private BigDecimal mustIssueAmt; // 可領金額
    private String payDate; // 首次核定日期
    private String payYms; // 首次發放起月
    private String payYme; // 首次發放迄月
    private String pauseYm; // 暫停續發年月
    private String unPauseYm; // 恢復續發年月
    private String notifyForm; // 核定通知書格式
    private String loanMk; // 不須抵銷紓困貸款註記
    private String mexcLvl; // 應決行層級
    private String realexcLvl; // 實際決行層級
    private String chkDate; // 審核日期
    private String chkMan; // 審核人員
    private String rechkDate; // 複核日期
    private String rechkMan; // 複核人員
    private String exeDate; // 決行日期
    private String exeMan; // 決行人員
    private String arcDate; // 歸檔日期
    private String arcPg; // 歸檔頁次
    private String closeDate; // 結案日期
    private String closeCause; // 結案原因
    private String promoteUser; // 承辦者代號
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String updTime; // 異動日期時間
    private String dupeIdnNoMk; // 身分證重號註記
    private String evtExpireDate; // 事故者屆齡日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期
    private BigDecimal oncPayYm; // 一次給付月數
    private BigDecimal lsInsmAmt; // 投保金額
    private BigDecimal oldRate; // 展延 / 減額比率
    private String oldRateSdate; // 展延 / 減額期間 (起)
    private String oldRateEdate; // 展延 / 減額期間 (迄)
    private String dabApNo; // 已領失能年金受理編號
    private BigDecimal dabAnnuAmt; // 已領失能年金金額
    private String lnChkMk; // 紓困貸款呆帳債務人註記
    private BigDecimal cutAmt;// 扣減/補償總金額
    private BigDecimal annuAmt;// 累計已領年金金額
    private BigDecimal issuCalcAmt;// 前次核定金額
    private String interValMonth;// 發放方式
    private String checkin;// 來源別(1:個人申辦  2:單位申辦)
    private byte[] consent;// PDF附件
    // ]
    private String appMonth;// 平均薪資月數
    private String realAvgMon;// 實際均薪月數

    private String specialAcc;// 專戶

    private BigDecimal marginAmt;// 老年差額金
    private String evtJobAge; // 事故年齡
    private BigDecimal samTY; // 同單位年資(年)
    private BigDecimal samTD; // 同單位年資(日)
    private BigDecimal onceTY; // 一次給付年資
    private BigDecimal oncePayM; // 一次給付月數
    private BigDecimal onceIssueAmt; // 一次給付金額
    
    // CIPB
    // [
    private BigDecimal avgWg;
    private BigDecimal nitrmY;
    private BigDecimal nitrmM;
    private BigDecimal oldTY;
    private BigDecimal oldTD;
    // ]

    // 受款人資料
    // [
    private List<OldAgeReviewRpt01BenDataCase> benList;
    // ]

    // 核定總額資料
    // [
    private OldAgeReviewRpt01IssueAmtDataCase issueAmtData;
    // ]

    // 給付資料
    // [
    private List<OldAgeReviewRpt01PayDataCase> payList;
    // ]

    // 核定資料
    // [
    private OldAgeReviewRpt01DecideDataCase decideData;
    // ]

    // 事故者死亡一次給付相關資料
    // [
    private OldAgeReviewRpt01DieOncePayDataCase dieOncePayData;
    // ]

    // 編審資料
    // [
    private List<OldAgeReviewRpt01ChkfileDataCase> chkfileDataList;
    private List<OldAgeReviewRpt01ChkfileDescCase> chkfileDescList;
    // ]

    // 核定通知書
    // [
    private OldAgeReviewRpt01NotifyDataCase notifyData;
    // ]

    // 請領同類給付資料
    // [
    private List<OldAgeReviewRpt01OncePayDataCase> oncePayList; // 一次給付
    private List<OldAgeReviewRpt01AnnuityPayDataCase> annuityPayList; // 年金給付
    // ]

    // 請領他類給付資料
    // [
    private List<OldAgeReviewRpt01DisablePayDataCase> disablePayList; // 申請失能給付記錄
    private List<OldAgeReviewRpt01DisableAnnuityPayDataCase> disableAnnuityPayList; // 申請失能年金記錄
    private List<OldAgeReviewRpt01DiePayDataCase> diePayList; // 申請死亡給付記錄
    private List<OldAgeReviewRpt01DiePayDataCaseBy9> diePayBy9List; // 申請死亡給付記錄-災保
    private List<OldAgeReviewRpt01SurvivorAnnuityPayDataCase> survivorAnnuityPayList; // 申請遺屬年金記錄
    private List<OldAgeReviewRpt01SurvivorAnnuityPayDataCaseBy9> survivorAnnuityPayBy9List; // 申請遺屬年金記錄 災保
    private List<OldAgeReviewRpt01InjuryPayDataCase> injuryPayList; // 申請傷病給付記錄
    private List<OldAgeReviewRpt01JoblessPayDataCase> joblessPayList; // 申請失業給付記錄
    private List<OldAgeReviewRpt01NpPayDataCase> npPayList; // 申請國保給付記錄
    // ]

    // 事故者於受款人給付資料的資料
    // [
    private List<OldAgeReviewRpt01BenPayDataCase> evtBenPayList;
    // ]

    // 承保異動資料
    // [
    private List<CiptUtilityCase> changeList;
    // ]

    // 投保單位資料
    // [
    private OldAgeReviewRpt01UnitCase applyUnitData; // 申請單位
    private OldAgeReviewRpt01UnitCase lastUnitData; // 最後單位
    // ]

    // 本次紓困貸款資料
    // [
    private OldAgeReviewRpt01LoanDataCase loanData;
    // ]

    // 另案扣減資料
    // [
    private List<OldAgeReviewRpt01DeductOnceDataCase> deductOnceList; // 一次給付
    private List<OldAgeReviewRpt01DeductAnnuityDataCase> deductAnnuityList; // 年金給付
    // ]

    // 平均薪資
    // [
    private List<OldAgeReviewRpt01MonthAvgAmtDataCase> monthAvgAmtList;
    private List<OldAgeReviewRpt01OnceAvgAmtDataCase> onceAvgAmtList;
    // ]

    /**
     * 受理日期
     */
    public String getCrtTimeString() {
        if (StringUtils.length(StringUtils.substring(crtTime, 0, 8)) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(StringUtils.substring(crtTime, 0, 8)), false);
        else
            return StringUtils.defaultString(StringUtils.substring(crtTime, 0, 8));
    }

    /**
     * 申請日期
     */
    public String getAppDateString() {
        if (StringUtils.length(appDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(appDate), false);
        else
            return StringUtils.defaultString(appDate);
    }

    /**
     * 受理編號
     * 
     * @return
     */
    public String getApNoString() {
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }

    /**
     * 首次發放起月
     * 
     * @return
     */
    public String getPayYmsString() {
        if (StringUtils.length(payYms) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(payYms), false);
        else
            return StringUtils.defaultString(payYms);
    }

    /**
     * 核定年月
     * 
     * @return
     */
    public String getIssuYmString() {
        if (StringUtils.length(issuYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), false);
        else
            return StringUtils.defaultString(issuYm);
    }

    /**
     * 事故者性別
     * 
     * @return
     */
    public String getEvtSexString() {
        if (StringUtils.equals(evtSex, "1"))
            return ConstantKey.BAAPPBASE_SEX_STR_1;
        else if (StringUtils.equals(evtSex, "2"))
            return ConstantKey.BAAPPBASE_SEX_STR_2;
        else
            return "";
    }

    /**
     * 事故者出生日期
     * 
     * @return
     */
    public String getEvtBrDateString() {
        if (StringUtils.length(evtBrDate) == 8) {
            String birthday = DateUtility.changeDateType(evtBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "民國前 " : "民國 ") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), true);
        }
        else {
            return StringUtils.defaultString(evtBrDate);
        }
    }

    /**
     * 事故者離職日期
     * 
     * @return
     */
    public String getEvtJobDateString() {
        if (StringUtils.length(evtJobDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtJobDate), false);
        else
            return StringUtils.defaultString(evtJobDate);
    }

    /**
     * 事故日期
     * 
     * @return
     */
    public String getEvtDateString() {
        if (StringUtils.length(evtDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtDate), false);
        else
            return StringUtils.defaultString(evtDate);
    }

    /**
     * 事故者符合日期
     * 
     * @return
     */
    public String getEvtEligibleDateString() {
        if (StringUtils.length(evtEligibleDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtEligibleDate), false);
        else
            return StringUtils.defaultString(evtEligibleDate);
    }

    /**
     * 事故者死亡日期
     * 
     * @return
     */
    public String getEvtDieDateString() {
        if (StringUtils.length(evtDieDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtDieDate), false);
        else
            return StringUtils.defaultString(evtDieDate);
    }

    /**
     * 事故者失蹤日期
     * 
     * @return
     */
    public String getEvtMissingDateString() {
        if (StringUtils.length(evtMissingDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtMissingDate), false);
        else
            return StringUtils.defaultString(evtMissingDate);
    }

    /**
     * 受益人出生日期
     * 
     * @return
     */
    public String getBenBrDateString() {
        if (StringUtils.length(benBrDate) == 8) {
            String birthday = DateUtility.changeDateType(benBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "-" : "") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), false);
        }
        else {
            return StringUtils.defaultString(benBrDate);
        }
    }

    /**
     * 法定代理人出生日期
     * 
     * @return
     */
    public String getGrdBrDateString() {
        if (StringUtils.length(grdBrDate) == 8) {
            String birthday = DateUtility.changeDateType(grdBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "-" : "") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), false);
        }
        else {
            return "";
        }
    }

    /**
     * 身分查核年月
     * 
     * @return
     */
    public String getIdnChkYmString() {
        if (StringUtils.length(idnChkYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(idnChkYm), false);
        else
            return StringUtils.defaultString(idnChkYm);
    }

    /**
     * 取得特定給付年月的編審註記資料 (用於受款人清單)
     * 
     * @param payYm 給付年月
     * @return
     */
    public String getChkfileDataBy(String payYm) {
        StringBuffer chkfileString = new StringBuffer("");
        for (OldAgeReviewRpt01ChkfileDataCase chkfileDataCase : chkfileDataList) {
            if (StringUtils.equals(chkfileDataCase.getPayYm(), payYm)) {
                // 只需顯示 BB 及 LN 註記
                if (StringUtils.equalsIgnoreCase(chkfileDataCase.getChkCode(), "BB") || StringUtils.equalsIgnoreCase(chkfileDataCase.getChkCode(), "LN")) {
                    if (StringUtils.isNotBlank(chkfileString.toString()))
                        chkfileString.append(" ");
                    chkfileString.append(StringUtils.defaultString(chkfileDataCase.getChkCode()));
                }
            }
        }
        return chkfileString.toString();
    }

    /**
     * 案件類別
     */
    public String getCaseTypString() {
        if ((ConstantKey.BAAPPBASE_CASETYP_1).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if ((ConstantKey.BAAPPBASE_CASETYP_2).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_CASETYP_3).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_CASETYP_4).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_CASETYP_5).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_CASETYP_6).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_CASETYP_A).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_A;
        else if ((ConstantKey.BAAPPBASE_CASETYP_B).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_B;
        else if ((ConstantKey.BAAPPBASE_CASETYP_C).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_C;
        else if ((ConstantKey.BAAPPBASE_CASETYP_D).equals(getCaseTyp()))
            return ConstantKey.BAAPPBASE_CASETYP_STR_D;
        else
            return "";
    }

    /**
     * 處理狀態
     */
    public String getProcStatString() {
        if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_00))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_00;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_01))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_01;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_10))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_10;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_11))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_11;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_12))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_12;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_13))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_13;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_20))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_20;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_30))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_30;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_40))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_40;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_50))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_50;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_80))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_80;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_90))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_90;
        else if (StringUtils.equals(procStat, ConstantKey.BAAPPBASE_PROCSTAT_99))
            return ConstantKey.BAAPPBASE_PROCSTAT_STR_99;
        else
            return "";
    }

    /**
     * 一次請領之年資採計方式
     */
    public String getOldSeniabString() {
        if (getOldSeniab().equals("1")) {
            return "按半年/一年計";
        }
        else if (getOldSeniab().equals("2")) {
            return "按比例計算";
        }
        else {
            return "";
        }
    }

    /**
     * 建構子
     */
    public OldAgeReviewRpt01Case() {

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

    public String getEncodeApItem() {
        if (StringUtils.equals("1", apItem))
            return ConstantKey.APITEM_PARAMCODE_1;
        if (StringUtils.equals("2", apItem))
            return ConstantKey.APITEM_PARAMCODE_2;
        if (StringUtils.equals("3", apItem))
            return ConstantKey.APITEM_PARAMCODE_3;
        return "";
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

    public String getOldSeniab() {
        return oldSeniab;
    }

    public void setOldSeniab(String oldSeniab) {
        this.oldSeniab = oldSeniab;
    }

    public BigDecimal getMustIssueAmt() {
        return mustIssueAmt;
    }

    public void setMustIssueAmt(BigDecimal mustIssueAmt) {
        this.mustIssueAmt = mustIssueAmt;
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

    public BigDecimal getOncPayYm() {
        return oncPayYm;
    }

    public void setOncPayYm(BigDecimal oncPayYm) {
        this.oncPayYm = oncPayYm;
    }

    public BigDecimal getLsInsmAmt() {
        return lsInsmAmt;
    }

    public void setLsInsmAmt(BigDecimal lsInsmAmt) {
        this.lsInsmAmt = lsInsmAmt;
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

    public List<OldAgeReviewRpt01BenDataCase> getBenList() {
        return benList;
    }

    public void setBenList(List<OldAgeReviewRpt01BenDataCase> benList) {
        this.benList = benList;
    }

    public List<OldAgeReviewRpt01PayDataCase> getPayList() {
        return payList;
    }

    public void setPayList(List<OldAgeReviewRpt01PayDataCase> payList) {
        this.payList = payList;
    }

    public OldAgeReviewRpt01DecideDataCase getDecideData() {
        return decideData;
    }

    public void setDecideData(OldAgeReviewRpt01DecideDataCase decideData) {
        this.decideData = decideData;
    }

    public List<OldAgeReviewRpt01ChkfileDataCase> getChkfileDataList() {
        return chkfileDataList;
    }

    public void setChkfileDataList(List<OldAgeReviewRpt01ChkfileDataCase> chkfileDataList) {
        this.chkfileDataList = chkfileDataList;
    }

    public List<OldAgeReviewRpt01ChkfileDescCase> getChkfileDescList() {
        return chkfileDescList;
    }

    public void setChkfileDescList(List<OldAgeReviewRpt01ChkfileDescCase> chkfileDescList) {
        this.chkfileDescList = chkfileDescList;
    }

    public List<OldAgeReviewRpt01BenPayDataCase> getEvtBenPayList() {
        return evtBenPayList;
    }

    public void setEvtBenPayList(List<OldAgeReviewRpt01BenPayDataCase> evtBenPayList) {
        this.evtBenPayList = evtBenPayList;
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

    public String getLnChkMk() {
        return lnChkMk;
    }

    public void setLnChkMk(String lnChkMk) {
        this.lnChkMk = lnChkMk;
    }

    public OldAgeReviewRpt01IssueAmtDataCase getIssueAmtData() {
        return issueAmtData;
    }

    public void setIssueAmtData(OldAgeReviewRpt01IssueAmtDataCase issueAmtData) {
        this.issueAmtData = issueAmtData;
    }

    public OldAgeReviewRpt01DieOncePayDataCase getDieOncePayData() {
        return dieOncePayData;
    }

    public void setDieOncePayData(OldAgeReviewRpt01DieOncePayDataCase dieOncePayData) {
        this.dieOncePayData = dieOncePayData;
    }

    public OldAgeReviewRpt01NotifyDataCase getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(OldAgeReviewRpt01NotifyDataCase notifyData) {
        this.notifyData = notifyData;
    }

    public List<OldAgeReviewRpt01OncePayDataCase> getOncePayList() {
        return oncePayList;
    }

    public void setOncePayList(List<OldAgeReviewRpt01OncePayDataCase> oncePayList) {
        this.oncePayList = oncePayList;
    }

    public List<OldAgeReviewRpt01AnnuityPayDataCase> getAnnuityPayList() {
        return annuityPayList;
    }

    public void setAnnuityPayList(List<OldAgeReviewRpt01AnnuityPayDataCase> annuityPayList) {
        this.annuityPayList = annuityPayList;
    }

    public List<OldAgeReviewRpt01DisablePayDataCase> getDisablePayList() {
        return disablePayList;
    }

    public void setDisablePayList(List<OldAgeReviewRpt01DisablePayDataCase> disablePayList) {
        this.disablePayList = disablePayList;
    }

    public List<OldAgeReviewRpt01DisableAnnuityPayDataCase> getDisableAnnuityPayList() {
        return disableAnnuityPayList;
    }

    public void setDisableAnnuityPayList(List<OldAgeReviewRpt01DisableAnnuityPayDataCase> disableAnnuityPayList) {
        this.disableAnnuityPayList = disableAnnuityPayList;
    }

    public List<OldAgeReviewRpt01SurvivorAnnuityPayDataCase> getSurvivorAnnuityPayList() {
        return survivorAnnuityPayList;
    }

    public void setSurvivorAnnuityPayList(List<OldAgeReviewRpt01SurvivorAnnuityPayDataCase> survivorAnnuityPayList) {
        this.survivorAnnuityPayList = survivorAnnuityPayList;
    }

    public List<OldAgeReviewRpt01DiePayDataCase> getDiePayList() {
        return diePayList;
    }

    public void setDiePayList(List<OldAgeReviewRpt01DiePayDataCase> diePayList) {
        this.diePayList = diePayList;
    }

    public List<OldAgeReviewRpt01InjuryPayDataCase> getInjuryPayList() {
        return injuryPayList;
    }

    public void setInjuryPayList(List<OldAgeReviewRpt01InjuryPayDataCase> injuryPayList) {
        this.injuryPayList = injuryPayList;
    }

    public List<OldAgeReviewRpt01JoblessPayDataCase> getJoblessPayList() {
        return joblessPayList;
    }

    public void setJoblessPayList(List<OldAgeReviewRpt01JoblessPayDataCase> joblessPayList) {
        this.joblessPayList = joblessPayList;
    }

    public List<OldAgeReviewRpt01NpPayDataCase> getNpPayList() {
        return npPayList;
    }

    public void setNpPayList(List<OldAgeReviewRpt01NpPayDataCase> npPayList) {
        this.npPayList = npPayList;
    }

    public List<CiptUtilityCase> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<CiptUtilityCase> changeList) {
        this.changeList = changeList;
    }

    public OldAgeReviewRpt01UnitCase getApplyUnitData() {
        return applyUnitData;
    }

    public void setApplyUnitData(OldAgeReviewRpt01UnitCase applyUnitData) {
        this.applyUnitData = applyUnitData;
    }

    public OldAgeReviewRpt01UnitCase getLastUnitData() {
        return lastUnitData;
    }

    public void setLastUnitData(OldAgeReviewRpt01UnitCase lastUnitData) {
        this.lastUnitData = lastUnitData;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public OldAgeReviewRpt01LoanDataCase getLoanData() {
        return loanData;
    }

    public void setLoanData(OldAgeReviewRpt01LoanDataCase loanData) {
        this.loanData = loanData;
    }

    public List<OldAgeReviewRpt01DeductOnceDataCase> getDeductOnceList() {
        return deductOnceList;
    }

    public void setDeductOnceList(List<OldAgeReviewRpt01DeductOnceDataCase> deductOnceList) {
        this.deductOnceList = deductOnceList;
    }

    public List<OldAgeReviewRpt01DeductAnnuityDataCase> getDeductAnnuityList() {
        return deductAnnuityList;
    }

    public void setDeductAnnuityList(List<OldAgeReviewRpt01DeductAnnuityDataCase> deductAnnuityList) {
        this.deductAnnuityList = deductAnnuityList;
    }

    public List<OldAgeReviewRpt01MonthAvgAmtDataCase> getMonthAvgAmtList() {
        return monthAvgAmtList;
    }

    public void setMonthAvgAmtList(List<OldAgeReviewRpt01MonthAvgAmtDataCase> monthAvgAmtList) {
        this.monthAvgAmtList = monthAvgAmtList;
    }
    
    public List<OldAgeReviewRpt01OnceAvgAmtDataCase> getOnceAvgAmtList() {
        return onceAvgAmtList;
    }

    public void setOnceAvgAmtList(List<OldAgeReviewRpt01OnceAvgAmtDataCase> onceAvgAmtList) {
        this.onceAvgAmtList = onceAvgAmtList;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public BigDecimal getIssuCalcAmt() {
        return issuCalcAmt;
    }

    public void setIssuCalcAmt(BigDecimal issuCalcAmt) {
        this.issuCalcAmt = issuCalcAmt;
    }

    public String getInterValMonth() {
        return interValMonth;
    }

    public void setInterValMonth(String interValMonth) {
        this.interValMonth = interValMonth;
    }

    public String getAppMonth() {
        return appMonth;
    }

    public void setAppMonth(String appMonth) {
        this.appMonth = appMonth;
    }

    public String getRealAvgMon() {
        return realAvgMon;
    }

    public void setRealAvgMon(String realAvgMon) {
        this.realAvgMon = realAvgMon;
    }

    public String getSpecialAcc() {
        return specialAcc;
    }

    public void setSpecialAcc(String specialAcc) {
        this.specialAcc = specialAcc;
    }

    public BigDecimal getMarginAmt() {
        return marginAmt;
    }

    public void setMarginAmt(BigDecimal marginAmt) {
        this.marginAmt = marginAmt;
    }

    public String getEvtJobAge() {
        return evtJobAge;
    }

    public void setEvtJobAge(String evtJobAge) {
        this.evtJobAge = evtJobAge;
    }

    public BigDecimal getSamTY() {
        return samTY;
    }

    public void setSamTY(BigDecimal samTY) {
        this.samTY = samTY;
    }

    public BigDecimal getSamTD() {
        return samTD;
    }

    public void setSamTD(BigDecimal samTD) {
        this.samTD = samTD;
    }

    public BigDecimal getOnceTY() {
        return onceTY;
    }

    public void setOnceTY(BigDecimal onceTY) {
        this.onceTY = onceTY;
    }

    public BigDecimal getOncePayM() {
        return oncePayM;
    }

    public void setOncePayM(BigDecimal oncePayM) {
        this.oncePayM = oncePayM;
    }

    public BigDecimal getOnceIssueAmt() {
        return onceIssueAmt;
    }

    public void setOnceIssueAmt(BigDecimal onceIssueAmt) {
        this.onceIssueAmt = onceIssueAmt;
    }
    
    public BigDecimal getAvgWg() {
        return avgWg;
    }

    public void setAvgWg(BigDecimal avgWg) {
        this.avgWg = avgWg;
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

    public BigDecimal getOldTY() {
        return oldTY;
    }

    public void setOldTY(BigDecimal oldTY) {
        this.oldTY = oldTY;
    }

    public BigDecimal getOldTD() {
        return oldTD;
    }

    public void setOldTD(BigDecimal oldTD) {
        this.oldTD = oldTD;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckinStr() {
        if(StringUtils.isNotBlank(checkin)) {
            if(StringUtils.equals(checkin, "1")) {
                return "【個人網路申辦】";
            }else if(StringUtils.equals(checkin, "2")) {
                return "【單位網路申辦】";
            }
            return "";
        }
        return "";
    }

    public byte[] getConsent() {
        return consent;
    }

    public void setConsent(byte[] consent) {
        this.consent = consent;
    }

	/**
	 * @return the diePayBy9List
	 */
	public List<OldAgeReviewRpt01DiePayDataCaseBy9> getDiePayBy9List() {
		return diePayBy9List;
	}

	/**
	 * @param diePayBy9List the diePayBy9List to set
	 */
	public void setDiePayBy9List(List<OldAgeReviewRpt01DiePayDataCaseBy9> diePayBy9List) {
		this.diePayBy9List = diePayBy9List;
	}

	/**
	 * @return the survivorAnnuityPayBy9List
	 */
	public List<OldAgeReviewRpt01SurvivorAnnuityPayDataCaseBy9> getSurvivorAnnuityPayBy9List() {
		return survivorAnnuityPayBy9List;
	}

	/**
	 * @param survivorAnnuityPayBy9List the survivorAnnuityPayBy9List to set
	 */
	public void setSurvivorAnnuityPayBy9List(
			List<OldAgeReviewRpt01SurvivorAnnuityPayDataCaseBy9> survivorAnnuityPayBy9List) {
		this.survivorAnnuityPayBy9List = survivorAnnuityPayBy9List;
	}
}
