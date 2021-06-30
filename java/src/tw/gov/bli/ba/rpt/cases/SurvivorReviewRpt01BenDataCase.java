package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.PresentationUtility;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 受款人資料
 * 
 * @author Rickychi
 */
public class SurvivorReviewRpt01BenDataCase implements Serializable {
    // 受款人資料
    // [
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
    private String payEeacc;// 銀行帳號
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
    private String oncePayMk;// 一次給付符合註記
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
    private String reSeniMk;// 保留年資
    private String offInsurDate;// 轉公保日期
    private String lawRetireDate;// 依法退休日期
    private String evtAppName;// 事故者申請時姓名
    private BigDecimal cutAmt;// 扣減/補償總金額
    private String accSeqNo;// 被共同具領之受款人員序號
    private String benMarrMk;// 婚姻狀況
    private String chgMk;// 更正註記
    private BigDecimal payAmts;// 首次核定總金額
    private String evtAppIdnNo;// 事故者申請時身分證號
    private String evtAppBrDate;// 事故者申請時出生日期
    private String oldAplDpt;// 申請代算單位
    
    private String marryDate;       //結婚日期    
    private String digamyDate;      //再婚日期
    private String monIncomeMk;     // 每月工作收入註記
    private BigDecimal monIncome;    //每月工作收入
    private String studMk;          //在學註記
    private String handIcapMk;      // 領有重度以上身心障礙手冊或證明
    private String adoPtDate;       // 收養日期
    private String raiseChildMk;    // 配偶扶養
    private String abanApsYm;       // 放棄請領起始年月
    private String relatChgDate;    // 親屬關係變動日期
    private String benMissingSdate; // 受益人失蹤期間(起)
    private String benMissingEdate; // 受益人失蹤期間(迄)
    private String prisonSdate;     // 監管期間(起)
    private String prisonEdate;     // 監管期間(迄)
    private String interDictDate;   // 受禁治產宣告日期
    private String repealInterdictDate;// 受禁治產(監護)宣告 - 撤銷日期
    private String ableApsYm;       //得請領起月  
    private String savingMk;        //計息存儲
    private String studSdate;       //在學起月
    private String studEdate;       //在學迄月  
    private BigDecimal termCount;   //在學段數
    private String handicapSdate;       //重殘起月
    private String handicapEdate;       //重殘迄月  
    private BigDecimal handicaptermCount;   //重殘段數
    private String interDictMk;     //禁制產註記
    private String raiseEvtMk;      // 被保險人扶養
    private String refBenName;      //繼承自
    private String schoolCode;      //學校代碼
    private String schoolCodeStr;   //學校代碼(中文)
    
    private String  benNationCodeName; //國籍名稱
    
    private String specialAcc;// 專戶
    
    // ]

    // 遺屬給付資料
    // [
    private List<SurvivorReviewRpt01BenPayDataCase> benPayDataList;
    // ]
    
    // 繼承給付資料
    // [
    private List<SurvivorReviewRpt01BenPayDataCase> benPayList;
    // ]
    
   
    // 遺屬編審註記資料
    // [
    private List<SurvivorReviewRpt01ChkfileDataCase> chkfileDataList;

    // ]
    
    // 遺屬符合註記資料
    // [
    private List<SurvivorReviewRpt01ChkfileDataCase> chkDataList;

    // ]
    
    
    /**
     * 事故者國籍別
     * 
     * @return
     */
    public String getBenNationTpeString(){
    	 String nationTypStr = "";
         if (StringUtils.isNotBlank(getBenNationTyp())) {
             if ((ConstantKey.BAAPPBASE_NATIONCODE_1).equals(getBenNationTyp())) {
            	 nationTypStr = ConstantKey.BAAPPBASE_NATIONCODE_STR_1;
             }
             else if ((ConstantKey.BAAPPBASE_NATIONCODE_2).equals(getBenNationTyp())) {
            	 nationTypStr = ConstantKey.BAAPPBASE_NATIONCODE_STR_2;
             }
         }
         return nationTypStr;  
    }
    
    /**
     * 身份查核年月
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
            return "";
        }
    }
    
    /**
     * 受益人死亡日期
     * 
     * @return
     */
    public String getBenDieDateString() {
        if (StringUtils.length(benDieDate) == 8) {
            String birthday = DateUtility.changeDateType(benDieDate);
            return ((StringUtils.contains(birthday, "-")) ? "-" : "") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), false);
        }
        else {
            return "";
        }
    }
    
    /**
     * 結案日期
     * 
     * @return
     */
    public String getCloseDateString() {
        if (StringUtils.length(closeDate) == 8) {
            String closedate = DateUtility.changeDateType(closeDate);
            return ((StringUtils.contains(closedate, "-")) ? "-" : "") + DateUtility.formatChineseDateString(closedate.substring(closedate.length() - 7, closedate.length()), false);
        }
        else {
            return "";
        }
    }    
    
    /**
     * 放棄請領起啟年月
     * 
     * @return
     */
    public String getAbanYmString() {
        if (StringUtils.length(abanApsYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(abanApsYm), false);
        else
            return StringUtils.defaultString(abanApsYm);
    }
    
    /**
     * 得請領年月
     * 
     * @return
     */
    public String getAbleApsYmString() {
        if (StringUtils.length(ableApsYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(ableApsYm), false);
        else
            return StringUtils.defaultString(ableApsYm);
    }
    
    /**
     * 結婚日期
     */
    public String getMarryDateString() {
        if (StringUtils.length(marryDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(marryDate), false);
        else
            return StringUtils.defaultString(marryDate);
    }
    
    /**
     * 再婚日期
     */
    public String getDigamyDateString() {
        if (StringUtils.length(digamyDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(digamyDate), false);
        else
            return StringUtils.defaultString(digamyDate);
    }
    
    
    /**
     * 親屬關係變動日期
     */
    public String getRelChgDateString() {
        if (StringUtils.length(relatChgDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(relatChgDate), false);
        else
            return StringUtils.defaultString(relatChgDate);
    }
   
       
    /**
     * 在學結束年月
     * 
     * @return
     */
    public String getStudEYmString() {
        if (StringUtils.length(studEdate) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(studEdate), false);
        else
            return StringUtils.defaultString(studEdate);
    }
    
    /**
     * 在學起始年月
     * 
     * @return
     */
    public String getStudSYmString() {
        if (StringUtils.length(studSdate) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(studSdate), false);
        else
            return StringUtils.defaultString(studSdate);
    }
    
    /**
     * 重殘結束年月
     * 
     * @return
     */
    public String getHandicapEYmString() {
        if (StringUtils.length(handicapEdate) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(handicapEdate), false);
        else
            return StringUtils.defaultString(handicapEdate);
    }
    
    /**
     * 重殘起始年月
     * 
     * @return
     */
    public String getHandicapSYmString() {
        if (StringUtils.length(handicapSdate) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(handicapSdate), false);
        else
            return StringUtils.defaultString(handicapSdate);
    }
    
    /**
     * 監管期間(迄)
     */
    public String getPrisEDateString() {
        if (StringUtils.length(prisonEdate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(prisonEdate), false);
        else
            return StringUtils.defaultString(prisonEdate);
    }
    
    /**
     * 監管期間(起)
     */
    public String getPrisSDateString() {
        if (StringUtils.length(prisonSdate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(prisonSdate), false);
        else
            return StringUtils.defaultString(prisonSdate);
    }
    
    
    /**
     * 受益人失蹤期間(迄)
     */
    public String getMisEDateString() {
        if (StringUtils.length(benMissingEdate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(benMissingEdate), false);
        else
            return StringUtils.defaultString(benMissingEdate);
    }
    
    /**
     * 受益人失蹤期間(起)
     */
    public String getMisSDateString() {
        if (StringUtils.length(benMissingSdate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(benMissingSdate), false);
        else
            return StringUtils.defaultString(benMissingSdate);
    }
    
    /**
     * 收養日期
     */
    public String getAdoPtDateString() {
        if (StringUtils.length(adoPtDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(adoPtDate), false);
        else
            return StringUtils.defaultString(adoPtDate);
    }
    
    /**
     * 受禁治產宣告日期
     */
    public String getInterDictDateString() {
        if (StringUtils.length(interDictDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(interDictDate), false);
        else
            return StringUtils.defaultString(interDictDate);
    }
    
    /**
     * 撤消禁治產宣告日期
     */
    public String getRepealInterDictDateString() {
        if (StringUtils.length(repealInterdictDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(repealInterdictDate), false);
        else
            return StringUtils.defaultString(repealInterdictDate);
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
     * 代辦人出生日期
     * 
     * @return
     */
    public String getAssignBrDateString() {
        if (StringUtils.length(assignBrDate) == 8) {
            String birthday = DateUtility.changeDateType(assignBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "-" : "") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), false);
        }
        else {
            return "";
        }
    }
    
    /**
     * 取得特定給付年月的編審註記資料
     * 
     * @param payYm 給付年月
     * @return
     */
    public String getChkfileDataBy(String payYm) {
        StringBuffer chkfileString = new StringBuffer("");
        for (SurvivorReviewRpt01ChkfileDataCase chkfileDataCase : chkfileDataList) {
            if (StringUtils.equals(chkfileDataCase.getPayYm(), payYm)) {
                if (StringUtils.isNotBlank(chkfileString.toString()))
                    chkfileString.append(" ");
                chkfileString.append(StringUtils.defaultString(chkfileDataCase.getChkCode()));
            }
        }
        return chkfileString.toString();
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
     * 事故者性別
     * 
     * @return
     */
    public String getBenSexString() {
        if (StringUtils.equals(benSex, "1"))
            return ConstantKey.BAAPPBASE_SEX_STR_1;
        else if (StringUtils.equals(benSex, "2"))
            return ConstantKey.BAAPPBASE_SEX_STR_2;
        else
            return "";
    }
    
    /**
     * 學校代碼 with 括號
     * 
     * @return
     */
    public String getSchoolCodeWithBrackets(){
         String schoolCode = "";
         if (StringUtils.isNotBlank(getSchoolCode())) {
             schoolCode = "(" + getSchoolCode() + ")";
         }
         return schoolCode;  
    }

    /**
     * 計息存儲
     */
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

    public String getOncePayMk() {
        return oncePayMk;
    }

    public void setOncePayMk(String oncePayMk) {
        this.oncePayMk = oncePayMk;
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

    public String getReSeniMk() {
        return reSeniMk;
    }

    public void setReSeniMk(String reSeniMk) {
        this.reSeniMk = reSeniMk;
    }

    public String getOffInsurDate() {
        return offInsurDate;
    }

    public void setOffInsurDate(String offInsurDate) {
        this.offInsurDate = offInsurDate;
    }

    public String getLawRetireDate() {
        return lawRetireDate;
    }

    public void setLawRetireDate(String lawRetireDate) {
        this.lawRetireDate = lawRetireDate;
    }

    public String getEvtAppName() {
        return evtAppName;
    }

    public void setEvtAppName(String evtAppName) {
        this.evtAppName = evtAppName;
    }

    public BigDecimal getCutAmt() {
        return cutAmt;
    }

    public void setCutAmt(BigDecimal cutAmt) {
        this.cutAmt = cutAmt;
    }

    public String getAccSeqNo() {
        return accSeqNo;
    }

    public void setAccSeqNo(String accSeqNo) {
        this.accSeqNo = accSeqNo;
    }

    public String getBenMarrMk() {
        return benMarrMk;
    }

    public void setBenMarrMk(String benMarrMk) {
        this.benMarrMk = benMarrMk;
    }

    public String getChgMk() {
        return chgMk;
    }

    public void setChgMk(String chgMk) {
        this.chgMk = chgMk;
    }

    public BigDecimal getPayAmts() {
        return payAmts;
    }

    public void setPayAmts(BigDecimal payAmts) {
        this.payAmts = payAmts;
    }

    public String getEvtAppIdnNo() {
        return evtAppIdnNo;
    }

    public void setEvtAppIdnNo(String evtAppIdnNo) {
        this.evtAppIdnNo = evtAppIdnNo;
    }

    public String getEvtAppBrDate() {
        return evtAppBrDate;
    }

    public void setEvtAppBrDate(String evtAppBrDate) {
        this.evtAppBrDate = evtAppBrDate;
    }

    public String getOldAplDpt() {
        return oldAplDpt;
    }

    public void setOldAplDpt(String oldAplDpt) {
        this.oldAplDpt = oldAplDpt;
    }

   
    public List<SurvivorReviewRpt01BenPayDataCase> getBenPayDataList() {
		return benPayDataList;
	}

	public void setBenPayDataList(
			List<SurvivorReviewRpt01BenPayDataCase> benPayDataList) {
		this.benPayDataList = benPayDataList;
	}

	public List<SurvivorReviewRpt01ChkfileDataCase> getChkfileDataList() {
        return chkfileDataList;
    }

    public void setChkfileDataList(List<SurvivorReviewRpt01ChkfileDataCase> chkfileDataList) {
        this.chkfileDataList = chkfileDataList;
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

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
    }

    public String getAdoPtDate() {
        return adoPtDate;
    }

    public void setAdoPtDate(String adoPtDate) {
        this.adoPtDate = adoPtDate;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
    }

    public String getAbanApsYm() {
        return abanApsYm;
    }

    public void setAbanApsYm(String abanApsYm) {
        this.abanApsYm = abanApsYm;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
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

    public String getInterDictDate() {
        return interDictDate;
    }

    public void setInterDictDate(String interDictDate) {
        this.interDictDate = interDictDate;
    }

    public String getAbleApsYm() {
        return ableApsYm;
    }

    public void setAbleApsYm(String ableApsYm) {
        this.ableApsYm = ableApsYm;
    }

    public String getSavingMk() {
        return savingMk;
    }

    public void setSavingMk(String savingMk) {
        this.savingMk = savingMk;
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

    public BigDecimal getTermCount() {
        return termCount;
    }

    public void setTermCount(BigDecimal termCount) {
        this.termCount = termCount;
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

    public BigDecimal getHandicaptermCount() {
        return handicaptermCount;
    }

    public void setHandicaptermCount(BigDecimal handicaptermCount) {
        this.handicaptermCount = handicaptermCount;
    }

    public String getInterDictMk() {
        return interDictMk;
    }

    public void setInterDictMk(String interDictMk) {
        this.interDictMk = interDictMk;
    }

    public String getRaiseEvtMk() {
        return raiseEvtMk;
    }

    public void setRaiseEvtMk(String raiseEvtMk) {
        this.raiseEvtMk = raiseEvtMk;
    }

    public List<SurvivorReviewRpt01ChkfileDataCase> getChkDataList() {
        return chkDataList;
    }

    public void setChkDataList(List<SurvivorReviewRpt01ChkfileDataCase> chkDataList) {
        this.chkDataList = chkDataList;
    }

    public String getRefBenName() {
        return refBenName;
    }

    public void setRefBenName(String refBenName) {
        this.refBenName = refBenName;
    }

	public String getBenNationCodeName() {
		return benNationCodeName;
	}

	public void setBenNationCodeName(String benNationCodeName) {
		this.benNationCodeName = benNationCodeName;
	}

	public List<SurvivorReviewRpt01BenPayDataCase> getBenPayList() {
		return benPayList;
	}

	public void setBenPayList(List<SurvivorReviewRpt01BenPayDataCase> benPayList) {
		this.benPayList = benPayList;
	}

	public String getRepealInterdictDate() {
		return repealInterdictDate;
	}

	public void setRepealInterdictDate(String repealInterdictDate) {
		this.repealInterdictDate = repealInterdictDate;
	}

	public String getSpecialAcc() {
		return specialAcc;
	}

	public void setSpecialAcc(String specialAcc) {
		this.specialAcc = specialAcc;
	}
    
    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }   

    public String getSchoolCodeStr() {
        return schoolCodeStr;
    }

    public void setSchoolCodeStr(String schoolCodeStr) {
        this.schoolCodeStr = schoolCodeStr;
    } 
    
}
