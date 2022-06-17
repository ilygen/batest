package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCheckFileCase;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01Case implements Serializable {

    // 事故者資料
    // [
    private BigDecimal baappbaseId; // 資料列編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String appDate; // 申請日期
    private String apItem; // 申請項目
    private String caseTyp; // 案件類別
    private String procStat; // 處理狀態
    private String acceptMk; // 電腦編審結果
    private String manchkMk; // 人工審核結果
    private String chgNote; // 更正原因
    private String apUbno; // 申請單位保險證號
    private String apubnock; // 申請單位保險證號檢查碼
    private String lsUbno; // 最後單位保險證號
    private String lsUbnoCk; // 老年-最後單位保險證號檢查碼;失能-事故發生單位保險證號檢查碼
    private String evtIdnNo; // 事故者身分證號
    private String evtIds; // 事故者社福識別碼
    private String evtName; // 事故者姓名
    private String evtBrDate; // 事故者出生日期
    private String evtSex; // 事故者性別
    private String evtNationTpe; // 事故者國籍別
    private String evtNationCode; // 事故者國籍
    private String evtJobDate; // 事故者離職日期
    private String evtDate; // 事故日期
    private String evtDieDate; // 事故者死亡日期
    private String evtExpireDate; // 事故者屆齡日期
    private String evtEligibleDate; // 事故者符合日期
    private String evtMissingDate; // 事故者失蹤日期
    private String evtAge; // 事故者申請時年齡
    private String benIdnNo; // 受益人身分證號
    private String benName; // 受益人姓名
    private String benBrDate; // 受益人出生日期
    private String benEvtRel; // 受益人與事故者關係
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
    private BigDecimal mitRate; // 匯款匯費
    private String grdIdnNo; // 法定代理人身分證號
    private String grdName; // 法定代理人姓名
    private String grdBrDate; // 法定代理人出生日期
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String payDate; // 首次核定日期
    private String payYms; // 首次發放起月
    private String payYme; // 首次發放迄月
    private String notifyForm; // 核定通知書格式
    private BigDecimal lsInsmAmt; // 投保金額
    private String crtUser; // 新增者代號
    private String crtTime; // 新增日期時間
    private String updUser; // 異動者代號
    private String dabApNo; // 已領失能年金受理編號
    private BigDecimal dabAnnuAmt; // 已領失能年金金額
    private String closeDate; // 結案日期
    private String closeCause; // 結案原因
    private String oncePayMk; // 一次給付符合註記
    private String lnChkMk; // 紓困貸款呆帳債務人註記
    private String dupeIdnNoMk; // 身分證重號註記
    private BigDecimal cutAmt; // 扣減/補償總金額
    private BigDecimal annuAmt; // 累計已領年金金額
    private String payKind; // 給付種類
    private String benMarrMk; // 婚姻狀況
    private String idnChkYm; // 身分查核年月
    private Boolean evtBenPayDataStatus; // 受款人給付資料是否顯示
    private Boolean evtBafamilyStatus; // 眷屬給付資料是否顯示
    private BigDecimal issuCalcAmt;// 前次核定金額
    private String interValMonth;// 發放方式
    private String mexcLvl; // 應決行層級
    private String ableapsYm; // 得請領起月
    private String appMonth;// 平均薪資月數
    private String realAvgMon;// 實際均薪月數
    private String checkin; // 來源別(1:個人申辦  2:單位申辦)
    private String sysCode;// 系統類別
    private String apnoFm;// 來源受理編號
    // ]
    private String specialAcc;// 專戶
    // 受款人資料
    // [
    private List<DisableReviewRpt01BenDataCase> benList;
    // ]

    // 失能資料
    // [
    private List<DisableRevewRpt01ExpDataCase> disableList;
    // ]

    // 眷屬資料
    // [
    private List<DisableReviewRpt01FamilyDataCase> famDataCaseList;
    // ]

    // 事故者於受款人給付資料的資料
    // [
    private List<DisableReviewRpt01BenPayDataCase> evtBenPayList;
    private List<DisableReviewRpt01BenPayDataCase> benByPayList;
    private List<DisableReviewRpt01BenPayDataCase> evtBenPayDataList;
    // ]

    // 核定總額資料
    // [
    private DisableReviewRpt01IssueAmtDataCase issueAmtData;
    // ]

    // 給付資料
    // [
    private List<DisableReviewRpt01PayDataCase> payList;
    // ]

    // 給付資料
    // [
    private List<DisableReviewRpt01NpData38Case> npData38List;
    // ]

    // 給付資料
    // [
    private List<DisableReviewRpt01NpData36Case> npData36List;
    // ]

    // 核定資料
    // [
    private DisableReviewRpt01DecideDataCase decideData;
    // ]

    // 編審資料
    // [
    private List<DisableReviewRpt01ChkfileDataCase> chkfileDataList;
    private List<DisableReviewRpt01ChkfileDescCase> chkfileDescList;
    // ]

    // 核定通知書
    // [
    private DisableReviewRpt01NotifyDataCase notifyData;
    // ]

    // 請領同類給付資料
    // [
    private List<DisableReviewRpt01OncePayDataCase> oncePayList; // 一次給付
    private List<DisableReviewRpt01OncePayDataCase> disasterOncePayList; // 一次給付 災保 災保失能給付
    private List<DisableReviewRpt01OncePayDataCase> disasterOncePay39List; // 一次給付 災保 退保後職業病失能津貼
    private List<DisableReviewRpt01OncePayDataCase> disasterOncePay3TList; // 一次給付 災保 退保後職業病失能津貼
    private List<DisableReviewRpt01OncePayDataCase> disasterOncePay3NList; // 一次給付 災保 未加保失能補助
    private List<DisableReviewRpt01OncePayDataCase> disasterOncePay3CList; // 一次給付 災保 未加保失能補助
    private List<DisableReviewRpt01AnnuityPayDataCase> disasterAnnuityPayList; // 年金給付 災保
    private List<DisableReviewRpt01OncePayDataCase> farmPayList; // 申請農保殘廢給付記錄
    private List<DisableReviewRpt01AnnuityPayDataCase> annuityPayList; // 年金給付
    private List<DisableReviewRpt01AnnuityPayDataCase> nbPayList; // 申請國保年金給付記錄
    private List<DisableReviewRpt01NpPayDataCase> nbDisPayList; // 申請國保身障年金給付記錄

    private List<CivilServantReviewRpt01DisablePayCase> civilServantDisablePayList; // 申請公保失能給付記錄
    private List<SoldierReviewRpt01DeadPayCase> soldierDisablePayList; // 申請軍保身心障礙給付記錄
    // ]

    // 請領他類給付資料
    // [
    private List<DisableReviewRpt01OldAgePayDataCase> oldAgePayList; // 老年年金給付
    private List<DisableReviewRpt01SurvivorPayDataCase> survivorPayList; // 遺屬年金給付
    private List<DisableReviewRpt01OldPayDataCase> oldPayList;// 老年給付
    private List<DisableReviewRpt01DisablePayDataCase> disablePayList; // 申請失能給付記錄
    private List<DisableReviewRpt01DiePayDataCase> diePayList; // 申請死亡給付記錄
    private List<DisableReviewRpt01DiePayDataCase> famDiePayList; // 申請農保死亡給付記錄
    private List<DisableReviewRpt01DiePayDataCase> disPayList; // 申請失蹤給付記錄
    private List<DisableReviewRpt01InjuryPayDataCase> injuryPayList; // 申請傷病給付記錄
    private List<DisableReviewRpt01JoblessPayDataCase> joblessPayList; // 申請失業給付記錄
    private List<DisableReviewRpt01JoblessPayDataCase> vocationalTrainingLivingAllowanceList; // 申請職業訓練生活津貼記錄
    private List<DisableReviewRpt01NpPayDataCase> npPayList; // 申請國保給付記錄
    private List<DisableReviewRpt01OncePayDataCase> hosPayList; // 申請職災住院醫療給付給付記錄
    private List<DisableReviewRpt01OncePayDataCase> disasterHosPayList; // 申請職災住院醫療給付給付記錄
    private List<DisableReviewRpt01DiePayDataCase> disasterDiePayList; // 申請死亡給付記錄 災保
    private List<DisableReviewRpt01DiePayDataCase> disasterDieForDiseaseAfterQuitPayList; // 退保後職業病死亡津貼 災保
    private List<DisableReviewRpt01DiePayDataCase> disasterDieWithoutPayList; // 未加保死亡補助 災保
    private List<DisableReviewRpt01DiePayDataCase> disasterLostPayList; // 申請失蹤給付記錄 災保
    private List<DisableReviewRpt01InjuryPayDataCase> disasterInjuryPayList; // 申請傷病給付記錄 災保
    private List<DisableReviewRpt01InjuryPayDataCase> disasterInjuryCarePayList; // 申請傷病給付記錄 災保
    private List<DisableReviewRpt01SurvivorPayDataCase> disasterSurvivorPayList; // 遺屬年金給付 災保

    private List<CivilServantReviewRpt01RetirementAnnuityPayCase> civilServantRetiredAnnuityPayList; // 公保養老年金給付 
    private List<CivilServantReviewRpt01RetirementAnnuityPayCase> civilServantRetiredSurvivorAnnuityPayList; // 公保養老遺屬年金給付
    private List<CivilServantReviewRpt01RetirementAnnuityPayCase> civilServantDeadSurvivorAnnuityPayList; // 公保死亡遺屬年金給付
    // ]

    // 另案扣減資料
    // [
    private List<DisableReviewRpt01DeductDataCase> deductList; // 一次給付
    private List<DisableReviewRpt01PayDeductDataCase> deductPayList; // 年金給付
    // ]

    // 承保異動資料
    // [
    private List<CiptUtilityCase> changeList;
    // ]

    // 重新查核失能程度資料
    // [
    private List<DisabledApplicationDataUpdateBareCheckCase> bareCheckList;
    // ]

    // 職災資料
    // [
    private DisableQueryOccAccDataCase occAcc;
    // ]

    // 投保單位資料
    // [
    private DisableReviewRpt01UnitCase applyUnitData; // 申請單位
    private DisableReviewRpt01UnitCase lastUnitData; // 最後單位
    // ]

    // 被保險人投保薪資
    // [
    private List<DisableReviewRpt01CipgDataCase> cipgData;
    // ]

    // 本次紓困貸款
    // [
    private List<DisableReviewRpt01LoanAmtCase> loanAmtData;
    // ]

    // 眷屬編審註記說明
    // [
    private List<DisableReviewRpt01FamChkfileDataCase> famChkDescDataList;
    // ]

    // 眷屬符合註記說明
    // [
    private List<DisableReviewRpt01FamChkfileDataCase> famChkDescList;
    // ]

    private DisableReviewRpt01OncePayDataCase dieOncePayData;

    // 國保資料 payKind=38
    private DisableReviewRpt01NpData38Case npData38;
    // 國保資料 payKind=36
    private DisableReviewRpt01NpData36Case npData36;

    private String labMerge;// 併計勞保年資

    // BACHKFILE - 編審註記資料
    private List<DisabledApplicationDataUpdateCheckFileCase> checkFileList;

    //

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
        if (StringUtils.length(issuYm) == 6) {
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), false);
        }
        else {
            return StringUtils.defaultString(issuYm);
        }
    }

    /**
     * 身分查核年月
     * 
     * @return
     */
    public String getIdnChkYmString() {
        if (StringUtils.length(idnChkYm) == 6) {
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(idnChkYm), false);
        }
        else {
            return StringUtils.defaultString(idnChkYm);
        }
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
    public String getEvtMissDateString() {
        if (StringUtils.length(evtMissingDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtMissingDate), false);
        else
            return StringUtils.defaultString(evtMissingDate);
    }

    /**
     * 取得特定給付年月的編審註記資料 (用於受款人清單)
     * 
     * @param payYm 給付年月
     * @return
     */
    public String getChkfileDataBy(String payYm) {
        StringBuffer chkfileString = new StringBuffer("");
        for (DisableReviewRpt01ChkfileDataCase chkfileDataCase : chkfileDataList) {
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
     * 結婚日期
     */
    public String getEvtJobDateDateString() {
        if (StringUtils.length(evtJobDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(evtJobDate), false);
        else
            return StringUtils.defaultString(evtJobDate);
    }

    /**
     * 結案日期
     */
    public String getCloseDateString() {
        if (StringUtils.length(closeDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(closeDate), false);
        else
            return StringUtils.defaultString(closeDate);
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
     * 編審註記資料 是否有 3M 註記
     * 
     * @return
     */
    public boolean isContainCheckMark3M() {
        if (checkFileList != null && checkFileList.size() > 0) {
            for (DisabledApplicationDataUpdateCheckFileCase checkFileData : checkFileList) {
                if (StringUtils.equalsIgnoreCase(checkFileData.getChkFileCode(), "3M"))
                    return true;
            }
        }

        return false;
    }

    /**
     * 加入一筆 編審註記資料 到 checkFileList
     * 
     * @param checkFileData
     */
    public void addCheckFileData(DisabledApplicationDataUpdateCheckFileCase checkFileData) {
        if (checkFileList == null)
            checkFileList = new ArrayList<DisabledApplicationDataUpdateCheckFileCase>();

        checkFileList.add(checkFileData);
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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApItem() {
        return apItem;
    }

    public void setApItem(String apItem) {
        this.apItem = apItem;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
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

    public String getApUbno() {
        return apUbno;
    }

    public void setApUbno(String apUbno) {
        this.apUbno = apUbno;
    }

    public String getLsUbno() {
        return lsUbno;
    }

    public void setLsUbno(String lsUbno) {
        this.lsUbno = lsUbno;
    }

    public String getEvtIdnNo() {
        return evtIdnNo;
    }

    public void setEvtIdnNo(String evtIdnNo) {
        this.evtIdnNo = evtIdnNo;
    }

    public String getEvtIds() {
        return evtIds;
    }

    public void setEvtIds(String evtIds) {
        this.evtIds = evtIds;
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

    public String getEvtAge() {
        return evtAge;
    }

    public void setEvtAge(String evtAge) {
        this.evtAge = evtAge;
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

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
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

    public String getNotifyForm() {
        return notifyForm;
    }

    public void setNotifyForm(String notifyForm) {
        this.notifyForm = notifyForm;
    }

    public BigDecimal getLsInsmAmt() {
        return lsInsmAmt;
    }

    public void setLsInsmAmt(BigDecimal lsInsmAmt) {
        this.lsInsmAmt = lsInsmAmt;
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

    public String getCloseCause() {
        return closeCause;
    }

    public void setCloseCause(String closeCause) {
        this.closeCause = closeCause;
    }

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
    }

    public String getLnChkMk() {
        return lnChkMk;
    }

    public void setLnChkMk(String lnChkMk) {
        this.lnChkMk = lnChkMk;
    }

    public String getDupeIdnNoMk() {
        return dupeIdnNoMk;
    }

    public void setDupeIdnNoMk(String dupeIdnNoMk) {
        this.dupeIdnNoMk = dupeIdnNoMk;
    }

    public DisableReviewRpt01IssueAmtDataCase getIssueAmtData() {
        return issueAmtData;
    }

    public void setIssueAmtData(DisableReviewRpt01IssueAmtDataCase issueAmtData) {
        this.issueAmtData = issueAmtData;
    }

    public List<DisableReviewRpt01PayDataCase> getPayList() {
        return payList;
    }

    public void setPayList(List<DisableReviewRpt01PayDataCase> payList) {
        this.payList = payList;
    }

    public DisableReviewRpt01DecideDataCase getDecideData() {
        return decideData;
    }

    public void setDecideData(DisableReviewRpt01DecideDataCase decideData) {
        this.decideData = decideData;
    }

    public List<DisableReviewRpt01ChkfileDataCase> getChkfileDataList() {
        return chkfileDataList;
    }

    public void setChkfileDataList(List<DisableReviewRpt01ChkfileDataCase> chkfileDataList) {
        this.chkfileDataList = chkfileDataList;
    }

    public List<DisableReviewRpt01ChkfileDescCase> getChkfileDescList() {
        return chkfileDescList;
    }

    public void setChkfileDescList(List<DisableReviewRpt01ChkfileDescCase> chkfileDescList) {
        this.chkfileDescList = chkfileDescList;
    }

    public DisableReviewRpt01NotifyDataCase getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(DisableReviewRpt01NotifyDataCase notifyData) {
        this.notifyData = notifyData;
    }

    public List<DisableReviewRpt01BenDataCase> getBenList() {
        return benList;
    }

    public void setBenList(List<DisableReviewRpt01BenDataCase> benList) {
        this.benList = benList;
    }

    public List<DisableRevewRpt01ExpDataCase> getDisableList() {
        return disableList;
    }

    public void setDisableList(List<DisableRevewRpt01ExpDataCase> disableList) {
        this.disableList = disableList;
    }

    public List<DisableReviewRpt01FamilyDataCase> getFamDataCaseList() {
        return famDataCaseList;
    }

    public void setFamDataCaseList(List<DisableReviewRpt01FamilyDataCase> famDataCaseList) {
        this.famDataCaseList = famDataCaseList;
    }

    public List<DisableReviewRpt01OncePayDataCase> getOncePayList() {
        return oncePayList;
    }

    public void setOncePayList(List<DisableReviewRpt01OncePayDataCase> oncePayList) {
        this.oncePayList = oncePayList;
    }

    public List<DisableReviewRpt01AnnuityPayDataCase> getAnnuityPayList() {
        return annuityPayList;
    }

    public void setAnnuityPayList(List<DisableReviewRpt01AnnuityPayDataCase> annuityPayList) {
        this.annuityPayList = annuityPayList;
    }

    public List<DisableReviewRpt01DisablePayDataCase> getDisablePayList() {
        return disablePayList;
    }

    public void setDisablePayList(List<DisableReviewRpt01DisablePayDataCase> disablePayList) {
        this.disablePayList = disablePayList;
    }

    public List<DisableReviewRpt01DiePayDataCase> getDiePayList() {
        return diePayList;
    }

    public void setDiePayList(List<DisableReviewRpt01DiePayDataCase> diePayList) {
        this.diePayList = diePayList;
    }

    public List<DisableReviewRpt01InjuryPayDataCase> getInjuryPayList() {
        return injuryPayList;
    }

    public void setInjuryPayList(List<DisableReviewRpt01InjuryPayDataCase> injuryPayList) {
        this.injuryPayList = injuryPayList;
    }

    public List<DisableReviewRpt01JoblessPayDataCase> getJoblessPayList() {
        return joblessPayList;
    }

    public void setJoblessPayList(List<DisableReviewRpt01JoblessPayDataCase> joblessPayList) {
        this.joblessPayList = joblessPayList;
    }

    public List<DisableReviewRpt01JoblessPayDataCase> getVocationalTrainingLivingAllowanceList() {
        return vocationalTrainingLivingAllowanceList;
    }

    public void setVocationalTrainingLivingAllowanceList(List<DisableReviewRpt01JoblessPayDataCase> vocationalTrainingLivingAllowanceList) {
        this.vocationalTrainingLivingAllowanceList = vocationalTrainingLivingAllowanceList;
    }

    public List<DisableReviewRpt01NpPayDataCase> getNpPayList() {
        return npPayList;
    }

    public void setNpPayList(List<DisableReviewRpt01NpPayDataCase> npPayList) {
        this.npPayList = npPayList;
    }

    public List<DisableReviewRpt01DeductDataCase> getDeductList() {
        return deductList;
    }

    public void setDeductList(List<DisableReviewRpt01DeductDataCase> deductList) {
        this.deductList = deductList;
    }

    public List<DisableReviewRpt01BenPayDataCase> getEvtBenPayList() {
        return evtBenPayList;
    }

    public void setEvtBenPayList(List<DisableReviewRpt01BenPayDataCase> evtBenPayList) {
        this.evtBenPayList = evtBenPayList;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public List<CiptUtilityCase> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<CiptUtilityCase> changeList) {
        this.changeList = changeList;
    }

    public String getApubnock() {
        return apubnock;
    }

    public void setApubnock(String apubnock) {
        this.apubnock = apubnock;
    }

    public String getLsUbnoCk() {
        return lsUbnoCk;
    }

    public void setLsUbnoCk(String lsUbnoCk) {
        this.lsUbnoCk = lsUbnoCk;
    }

    public String getAbleapsYm() {
        return ableapsYm;
    }

    public void setAbleapsYm(String ableapsYm) {
        this.ableapsYm = ableapsYm;
    }

    public DisableQueryOccAccDataCase getOccAcc() {
        return occAcc;
    }

    public void setOccAcc(DisableQueryOccAccDataCase occAcc) {
        this.occAcc = occAcc;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public DisableReviewRpt01OncePayDataCase getDieOncePayData() {
        return dieOncePayData;
    }

    public void setDieOncePayData(DisableReviewRpt01OncePayDataCase dieOncePayData) {
        this.dieOncePayData = dieOncePayData;
    }

    public DisableReviewRpt01UnitCase getApplyUnitData() {
        return applyUnitData;
    }

    public void setApplyUnitData(DisableReviewRpt01UnitCase applyUnitData) {
        this.applyUnitData = applyUnitData;
    }

    public DisableReviewRpt01UnitCase getLastUnitData() {
        return lastUnitData;
    }

    public void setLastUnitData(DisableReviewRpt01UnitCase lastUnitData) {
        this.lastUnitData = lastUnitData;
    }

    public String getEvtNationCode() {
        return evtNationCode;
    }

    public void setEvtNationCode(String evtNationCode) {
        this.evtNationCode = evtNationCode;
    }

    public BigDecimal getAnnuAmt() {
        return annuAmt;
    }

    public void setAnnuAmt(BigDecimal annuAmt) {
        this.annuAmt = annuAmt;
    }

    public List<DisableReviewRpt01CipgDataCase> getCipgData() {
        return cipgData;
    }

    public void setCipgData(List<DisableReviewRpt01CipgDataCase> cipgData) {
        this.cipgData = cipgData;
    }

    public List<DisableReviewRpt01LoanAmtCase> getLoanAmtData() {
        return loanAmtData;
    }

    public void setLoanAmtData(List<DisableReviewRpt01LoanAmtCase> loanAmtData) {
        this.loanAmtData = loanAmtData;
    }

    public List<DisableReviewRpt01FamChkfileDataCase> getFamChkDescDataList() {
        return famChkDescDataList;
    }

    public void setFamChkDescDataList(List<DisableReviewRpt01FamChkfileDataCase> famChkDescDataList) {
        this.famChkDescDataList = famChkDescDataList;
    }

    public List<DisableReviewRpt01FamChkfileDataCase> getFamChkDescList() {
        return famChkDescList;
    }

    public void setFamChkDescList(List<DisableReviewRpt01FamChkfileDataCase> famChkDescList) {
        this.famChkDescList = famChkDescList;
    }

    public List<DisableReviewRpt01OncePayDataCase> getFarmPayList() {
        return farmPayList;
    }

    public void setFarmPayList(List<DisableReviewRpt01OncePayDataCase> farmPayList) {
        this.farmPayList = farmPayList;
    }

    public List<DisableReviewRpt01AnnuityPayDataCase> getNbPayList() {
        return nbPayList;
    }

    public void setNbPayList(List<DisableReviewRpt01AnnuityPayDataCase> nbPayList) {
        this.nbPayList = nbPayList;
    }

    public List<DisableReviewRpt01OldAgePayDataCase> getOldAgePayList() {
        return oldAgePayList;
    }

    public void setOldAgePayList(List<DisableReviewRpt01OldAgePayDataCase> oldAgePayList) {
        this.oldAgePayList = oldAgePayList;
    }

    public List<DisableReviewRpt01SurvivorPayDataCase> getSurvivorPayList() {
        return survivorPayList;
    }

    public void setSurvivorPayList(List<DisableReviewRpt01SurvivorPayDataCase> survivorPayList) {
        this.survivorPayList = survivorPayList;
    }

    public List<DisableReviewRpt01OldPayDataCase> getOldPayList() {
        return oldPayList;
    }

    public void setOldPayList(List<DisableReviewRpt01OldPayDataCase> oldPayList) {
        this.oldPayList = oldPayList;
    }

    public List<DisableReviewRpt01PayDeductDataCase> getDeductPayList() {
        return deductPayList;
    }

    public void setDeductPayList(List<DisableReviewRpt01PayDeductDataCase> deductPayList) {
        this.deductPayList = deductPayList;
    }

    public List<DisableReviewRpt01DiePayDataCase> getDisPayList() {
        return disPayList;
    }

    public void setDisPayList(List<DisableReviewRpt01DiePayDataCase> disPayList) {
        this.disPayList = disPayList;
    }

    public List<DisableReviewRpt01DiePayDataCase> getFamDiePayList() {
        return famDiePayList;
    }

    public void setFamDiePayList(List<DisableReviewRpt01DiePayDataCase> famDiePayList) {
        this.famDiePayList = famDiePayList;
    }

    public List<DisableReviewRpt01BenPayDataCase> getEvtBenPayDataList() {
        return evtBenPayDataList;
    }

    public void setEvtBenPayDataList(List<DisableReviewRpt01BenPayDataCase> evtBenPayDataList) {
        this.evtBenPayDataList = evtBenPayDataList;
    }

    public String getPayKind() {
        return payKind;
    }

    public void setPayKind(String payKind) {
        this.payKind = payKind;
    }

    public Boolean getEvtBenPayDataStatus() {
        return evtBenPayDataStatus;
    }

    public void setEvtBenPayDataStatus(Boolean evtBenPayDataStatus) {
        this.evtBenPayDataStatus = evtBenPayDataStatus;
    }

    public String getBenMarrMk() {
        return benMarrMk;
    }

    public void setBenMarrMk(String benMarrMk) {
        this.benMarrMk = benMarrMk;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public Boolean getEvtBafamilyStatus() {
        return evtBafamilyStatus;
    }

    public void setEvtBafamilyStatus(Boolean evtBafamilyStatus) {
        this.evtBafamilyStatus = evtBafamilyStatus;
    }

    public List<DisableReviewRpt01NpPayDataCase> getNbDisPayList() {
        return nbDisPayList;
    }

    public void setNbDisPayList(List<DisableReviewRpt01NpPayDataCase> nbDisPayList) {
        this.nbDisPayList = nbDisPayList;
    }

    public List<DisableReviewRpt01OncePayDataCase> getHosPayList() {
        return hosPayList;
    }

    public void setHosPayList(List<DisableReviewRpt01OncePayDataCase> hosPayList) {
        this.hosPayList = hosPayList;
    }

    public List<DisableReviewRpt01BenPayDataCase> getBenByPayList() {
        return benByPayList;
    }

    public void setBenByPayList(List<DisableReviewRpt01BenPayDataCase> benByPayList) {
        this.benByPayList = benByPayList;
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

    public String getMexcLvl() {
        return mexcLvl;
    }

    public void setMexcLvl(String mexcLvl) {
        this.mexcLvl = mexcLvl;
    }

    public DisableReviewRpt01NpData38Case getNpData38() {
        return npData38;
    }

    public void setNpData38(DisableReviewRpt01NpData38Case npData38) {
        this.npData38 = npData38;
    }

    public DisableReviewRpt01NpData36Case getNpData36() {
        return npData36;
    }

    public void setNpData36(DisableReviewRpt01NpData36Case npData36) {
        this.npData36 = npData36;
    }

    public String getLabMerge() {
        return labMerge;
    }

    public void setLabMerge(String labMerge) {
        this.labMerge = labMerge;
    }

    public List<DisableReviewRpt01NpData38Case> getNpData38List() {
        return npData38List;
    }

    public void setNpData38List(List<DisableReviewRpt01NpData38Case> npData38List) {
        this.npData38List = npData38List;
    }

    public List<DisableReviewRpt01NpData36Case> getNpData36List() {
        return npData36List;
    }

    public void setNpData36List(List<DisableReviewRpt01NpData36Case> npData36List) {
        this.npData36List = npData36List;
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

    public List<DisabledApplicationDataUpdateBareCheckCase> getBareCheckList() {
        return bareCheckList;
    }

    public void setBareCheckList(List<DisabledApplicationDataUpdateBareCheckCase> bareCheckList) {
        this.bareCheckList = bareCheckList;
    }

    public List<DisabledApplicationDataUpdateCheckFileCase> getCheckFileList() {
        return checkFileList;
    }

    public void setCheckFileList(List<DisabledApplicationDataUpdateCheckFileCase> checkFileList) {
        this.checkFileList = checkFileList;
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

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getApnoFm() {
		return apnoFm;
	}

	public void setApnoFm(String apnoFm) {
		this.apnoFm = apnoFm;
	}

	/**
	 * @return the disasterOncePayList
	 */
	public List<DisableReviewRpt01OncePayDataCase> getDisasterOncePayList() {
		return disasterOncePayList;
	}

	/**
	 * @param disasterOncePayList the disasterOncePayList to set
	 */
	public void setDisasterOncePayList(List<DisableReviewRpt01OncePayDataCase> disasterOncePayList) {
		this.disasterOncePayList = disasterOncePayList;
	}

	/**
	 * @return the disasterOncePay3TList
	 */
	public List<DisableReviewRpt01OncePayDataCase> getDisasterOncePay3TList() {
		return disasterOncePay3TList;
	}

	/**
	 * @param disasterOncePay3TList the disasterOncePay3TList to set
	 */
	public void setDisasterOncePay3TList(List<DisableReviewRpt01OncePayDataCase> disasterOncePay3TList) {
		this.disasterOncePay3TList = disasterOncePay3TList;
	}

	/**
	 * @return the disasterOncePay3NList
	 */
	public List<DisableReviewRpt01OncePayDataCase> getDisasterOncePay3NList() {
		return disasterOncePay3NList;
	}

	/**
	 * @param disasterOncePay3NList the disasterOncePay3NList to set
	 */
	public void setDisasterOncePay3NList(List<DisableReviewRpt01OncePayDataCase> disasterOncePay3NList) {
		this.disasterOncePay3NList = disasterOncePay3NList;
	}

	/**
	 * @return the disasterOncePay39List
	 */
	public List<DisableReviewRpt01OncePayDataCase> getDisasterOncePay39List() {
		return disasterOncePay39List;
	}

	/**
	 * @param disasterOncePay39List the disasterOncePay39List to set
	 */
	public void setDisasterOncePay39List(List<DisableReviewRpt01OncePayDataCase> disasterOncePay39List) {
		this.disasterOncePay39List = disasterOncePay39List;
	}

	/**
	 * @return the disasterInjuryPayList
	 */
	public List<DisableReviewRpt01InjuryPayDataCase> getDisasterInjuryPayList() {
		return disasterInjuryPayList;
	}

	/**
	 * @param disasterInjuryPayList the disasterInjuryPayList to set
	 */
	public void setDisasterInjuryPayList(List<DisableReviewRpt01InjuryPayDataCase> disasterInjuryPayList) {
		this.disasterInjuryPayList = disasterInjuryPayList;
	}

	/**
	 * @return the disasterDiePayList
	 */
	public List<DisableReviewRpt01DiePayDataCase> getDisasterDiePayList() {
		return disasterDiePayList;
	}

	/**
	 * @param disasterDiePayList the disasterDiePayList to set
	 */
	public void setDisasterDiePayList(
			List<DisableReviewRpt01DiePayDataCase> disasterDiePayList) {
		this.disasterDiePayList = disasterDiePayList;
	}

	/**
	 * @return the disasterLostPayList
	 */
	public List<DisableReviewRpt01DiePayDataCase> getDisasterLostPayList() {
		return disasterLostPayList;
	}

	/**
	 * @param disasterLostPayList the disasterLostPayList to set
	 */
	public void setDisasterLostPayList(
			List<DisableReviewRpt01DiePayDataCase> disasterLostPayList) {
		this.disasterLostPayList = disasterLostPayList;
	}

	/**
	 * @return the disasterDieForDiseaseAfterQuitPayList
	 */
	public List<DisableReviewRpt01DiePayDataCase> getDisasterDieForDiseaseAfterQuitPayList() {
		return disasterDieForDiseaseAfterQuitPayList;
	}

	/**
	 * @param disasterDieForDiseaseAfterQuitPayList the disasterDieForDiseaseAfterQuitPayList to set
	 */
	public void setDisasterDieForDiseaseAfterQuitPayList(
			List<DisableReviewRpt01DiePayDataCase> disasterDieForDiseaseAfterQuitPayList) {
		this.disasterDieForDiseaseAfterQuitPayList = disasterDieForDiseaseAfterQuitPayList;
	}

	/**
	 * @return the disasterDieWithoutPayList
	 */
	public List<DisableReviewRpt01DiePayDataCase> getDisasterDieWithoutPayList() {
		return disasterDieWithoutPayList;
	}

	/**
	 * @param disasterDieWithoutPayList the disasterDieWithoutPayList to set
	 */
	public void setDisasterDieWithoutPayList(
			List<DisableReviewRpt01DiePayDataCase> disasterDieWithoutPayList) {
		this.disasterDieWithoutPayList = disasterDieWithoutPayList;
	}

	/**
	 * @return the disasterOncePay3CList
	 */
	public List<DisableReviewRpt01OncePayDataCase> getDisasterOncePay3CList() {
		return disasterOncePay3CList;
	}

	/**
	 * @param disasterOncePay3CList the disasterOncePay3CList to set
	 */
	public void setDisasterOncePay3CList(List<DisableReviewRpt01OncePayDataCase> disasterOncePay3CList) {
		this.disasterOncePay3CList = disasterOncePay3CList;
	}

	/**
	 * @return the disasterAnnuityPayList
	 */
	public List<DisableReviewRpt01AnnuityPayDataCase> getDisasterAnnuityPayList() {
		return disasterAnnuityPayList;
	}

	/**
	 * @param disasterAnnuityPayList the disasterAnnuityPayList to set
	 */
	public void setDisasterAnnuityPayList(List<DisableReviewRpt01AnnuityPayDataCase> disasterAnnuityPayList) {
		this.disasterAnnuityPayList = disasterAnnuityPayList;
	}

	/**
	 * @return the disasterSurvivorPayList
	 */
	public List<DisableReviewRpt01SurvivorPayDataCase> getDisasterSurvivorPayList() {
		return disasterSurvivorPayList;
	}

	/**
	 * @param disasterSurvivorPayList the disasterSurvivorPayList to set
	 */
	public void setDisasterSurvivorPayList(List<DisableReviewRpt01SurvivorPayDataCase> disasterSurvivorPayList) {
		this.disasterSurvivorPayList = disasterSurvivorPayList;
	}

	/**
	 * @return the disasterInjuryCarePayList
	 */
	public List<DisableReviewRpt01InjuryPayDataCase> getDisasterInjuryCarePayList() {
		return disasterInjuryCarePayList;
	}

	/**
	 * @param disasterInjuryCarePayList the disasterInjuryCarePayList to set
	 */
	public void setDisasterInjuryCarePayList(List<DisableReviewRpt01InjuryPayDataCase> disasterInjuryCarePayList) {
		this.disasterInjuryCarePayList = disasterInjuryCarePayList;
	}

	/**
	 * @return the civilServantDisablePayList
	 */
	public List<CivilServantReviewRpt01DisablePayCase> getCivilServantDisablePayList() {
		return civilServantDisablePayList;
	}

	/**
	 * @param civilServantDisablePayList the civilServantDisablePayList to set
	 */
	public void setCivilServantDisablePayList(List<CivilServantReviewRpt01DisablePayCase> civilServantDisablePayList) {
		this.civilServantDisablePayList = civilServantDisablePayList;
	}

	/**
	 * @return the soldierDisablePayList
	 */
	public List<SoldierReviewRpt01DeadPayCase> getSoldierDisablePayList() {
		return soldierDisablePayList;
	}

	/**
	 * @param soldierDisablePayList the soldierDisablePayList to set
	 */
	public void setSoldierDisablePayList(List<SoldierReviewRpt01DeadPayCase> soldierDisablePayList) {
		this.soldierDisablePayList = soldierDisablePayList;
	}

	/**
	 * @return the civilServantRetiredAnnuityPayList
	 */
	public List<CivilServantReviewRpt01RetirementAnnuityPayCase> getCivilServantRetiredAnnuityPayList() {
		return civilServantRetiredAnnuityPayList;
	}

	/**
	 * @param civilServantRetiredAnnuityPayList the civilServantRetiredAnnuityPayList to set
	 */
	public void setCivilServantRetiredAnnuityPayList(
			List<CivilServantReviewRpt01RetirementAnnuityPayCase> civilServantRetiredAnnuityPayList) {
		this.civilServantRetiredAnnuityPayList = civilServantRetiredAnnuityPayList;
	}

	/**
	 * @return the civilServantRetiredSurvivorAnnuityPayList
	 */
	public List<CivilServantReviewRpt01RetirementAnnuityPayCase> getCivilServantRetiredSurvivorAnnuityPayList() {
		return civilServantRetiredSurvivorAnnuityPayList;
	}

	/**
	 * @param civilServantRetiredSurvivorAnnuityPayList the civilServantRetiredSurvivorAnnuityPayList to set
	 */
	public void setCivilServantRetiredSurvivorAnnuityPayList(
			List<CivilServantReviewRpt01RetirementAnnuityPayCase> civilServantRetiredSurvivorAnnuityPayList) {
		this.civilServantRetiredSurvivorAnnuityPayList = civilServantRetiredSurvivorAnnuityPayList;
	}

	/**
	 * @return the civilServantDeadSurvivorAnnuityPayList
	 */
	public List<CivilServantReviewRpt01RetirementAnnuityPayCase> getCivilServantDeadSurvivorAnnuityPayList() {
		return civilServantDeadSurvivorAnnuityPayList;
	}

	/**
	 * @param civilServantDeadSurvivorAnnuityPayList the civilServantDeadSurvivorAnnuityPayList to set
	 */
	public void setCivilServantDeadSurvivorAnnuityPayList(
			List<CivilServantReviewRpt01RetirementAnnuityPayCase> civilServantDeadSurvivorAnnuityPayList) {
		this.civilServantDeadSurvivorAnnuityPayList = civilServantDeadSurvivorAnnuityPayList;
	}

	/**
	 * @return the disasterHosPayList
	 */
	public List<DisableReviewRpt01OncePayDataCase> getDisasterHosPayList() {
		return disasterHosPayList;
	}

	/**
	 * @param disasterHosPayList the disasterHosPayList to set
	 */
	public void setDisasterHosPayList(List<DisableReviewRpt01OncePayDataCase> disasterHosPayList) {
		this.disasterHosPayList = disasterHosPayList;
	}
	
}
