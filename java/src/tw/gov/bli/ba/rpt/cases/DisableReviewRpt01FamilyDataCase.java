package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保失能年金給付受理編審清單 - 眷屬資料
 * 
 * @author Evelyn Hsu
 */

public class DisableReviewRpt01FamilyDataCase implements Serializable {
    
    private BigDecimal baappbaseId; // 給付主檔資料編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String famAppDate; // 眷屬申請日期
    private String famIdnNo; // 眷屬身分證號
    private String famName; // 眷屬姓名
    private String famBrDate; // 眷屬出生日期
    private String famSex; // 眷屬性別
    private String famEvtRel; // 眷屬與事故者關係
    private String famDieDate; // 眷屬死亡日期
    private String relatChgDate; // 親屬關係變動日期
    private String raiseChildMk; // 配偶扶養
    private String monIncomeMk; // 每月工作收入註記
    private BigDecimal monIncome; // 每月工作收入
    private String marryDate; // 結婚日期
    private String divorceDate; // 離婚日期
    private String interDictMk; // 受禁治產宣告註記
    private String interDictDate; // 受禁治產宣告日期
    private String repealInterDictDate; // 受禁治產撤消日期
    private String adoPtDate; // 收養日期
    private String benMissingSdate; // 受益人失蹤期間(起)
    private String benMissingEdate; // 受益人失蹤期間(迄)
    private String prisonSdate; // 監管期間(起)
    private String prisonEdate; // 監管期間(迄)
    private String studMk; // 在學
    private String studSdate; // 在學起始年月
    private String studEdate; // 在學結束年月
    private String abanApsYm; // 放棄請領起始年月
    private String handIcapMk;// 領有重度以上身心障礙手冊或證明
    private String closeDate;// 結案日期
    private String closeCause;// 結案原因
    private String famNationTyp;// 眷屬國籍別
    private String famNationCode;// 眷屬國籍
    private String idnChkYm; //身份查核年月
    private BigDecimal studDataCount; //段落
    private String ableApsYm; // 得請領起月
    private String schoolCode;      //學校代碼
    private String schoolCodeStr;   //學校代碼(中文)
    
    
    /**
     * 申請日期
     */
    public String getFamAppDateString() {
        if (StringUtils.length(famAppDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(famAppDate), false);
        else
            return StringUtils.defaultString(famAppDate);
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
     *  身份查核年月
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
     * 結婚日期
     */
    public String getMarryDateString() {
        if (StringUtils.length(marryDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(marryDate), false);
        else
            return StringUtils.defaultString(marryDate);
    }
    
    /**
     * 離婚日期
     */
    public String getDivorceDateString() {
        if (StringUtils.length(divorceDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(divorceDate), false);
        else
            return StringUtils.defaultString(divorceDate);
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
     * 受禁治產撤消日期
     */
    public String getRepealInterDictDateString() {
        if (StringUtils.length(repealInterDictDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(repealInterDictDate), false);
        else
            return StringUtils.defaultString(repealInterDictDate);
    }
    
    /**
     * 死亡日期日期
     */
    public String getFamDieDateString() {
        if (StringUtils.length(famDieDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(famDieDate), false);
        else
            return StringUtils.defaultString(famDieDate);
    }
    
    /**
     * 得請領起月
     */
    public String getAbleApsYmStr() {
        if (StringUtils.isNotBlank(getAbleApsYm())) {
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(getAbleApsYm()), false);                   
        }
        else {
            return getAbleApsYm();
        }
    }
    
    /**
    * 死亡日期日期
    */
   public String getCloseDateString() {
       if (StringUtils.length(closeDate) == 8)
           return DateUtility.formatChineseDateString(DateUtility.changeDateType(closeDate), false);
       else
           return StringUtils.defaultString(closeDate);
   }
    
    /**
     * 眷屬出生日期
     * 
     * @return
     */
    public String getFamBrDateString() {
        if (StringUtils.length(famBrDate) == 8) {
            String birthday = DateUtility.changeDateType(famBrDate);
            return ((StringUtils.contains(birthday, "-")) ? "-" : "") + DateUtility.formatChineseDateString(birthday.substring(birthday.length() - 7, birthday.length()), false);
        }
        else {
            return StringUtils.defaultString(famBrDate);
        }
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
    
    // 眷屬編審註記資料
    // [
    private List<DisableReviewRpt01FamChkfileDataCase> famChkfileDataList;
    // ]
    
    // 眷屬符合註記資料
    // [
    private List<DisableReviewRpt01FamChkfileDataCase> famChkfileDescList;
    // ]
    
    // 眷屬編審註記說明
    
    private List<DisableReviewRpt01FamChkfileDataCase> famChkDescDataList;
    
    // 眷屬符合註記說明
    
    private List<DisableReviewRpt01FamChkfileDataCase> famChkDescList;
    
    public DisableReviewRpt01FamilyDataCase(){
        
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

    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public String getFamDieDate() {
        return famDieDate;
    }

    public void setFamDieDate(String famDieDate) {
        this.famDieDate = famDieDate;
    }

    public String getRelatChgDate() {
        return relatChgDate;
    }

    public void setRelatChgDate(String relatChgDate) {
        this.relatChgDate = relatChgDate;
    }

    public String getRaiseChildMk() {
        return raiseChildMk;
    }

    public void setRaiseChildMk(String raiseChildMk) {
        this.raiseChildMk = raiseChildMk;
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

    public String getMarryDate() {
        return marryDate;
    }

    public void setMarryDate(String marryDate) {
        this.marryDate = marryDate;
    }

    public String getDivorceDate() {
        return divorceDate;
    }

    public void setDivorceDate(String divorceDate) {
        this.divorceDate = divorceDate;
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

    public String getAdoPtDate() {
        return adoPtDate;
    }

    public void setAdoPtDate(String adoPtDate) {
        this.adoPtDate = adoPtDate;
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

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
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

    public List<DisableReviewRpt01FamChkfileDataCase> getFamChkfileDataList() {
        return famChkfileDataList;
    }

    public void setFamChkfileDataList(List<DisableReviewRpt01FamChkfileDataCase> famChkfileDataList) {
        this.famChkfileDataList = famChkfileDataList;
    }

    public String getAbanApsYm() {
        return abanApsYm;
    }

    public void setAbanApsYm(String abanApsYm) {
        this.abanApsYm = abanApsYm;
    }

    public List<DisableReviewRpt01FamChkfileDataCase> getFamChkfileDescList() {
        return famChkfileDescList;
    }

    public void setFamChkfileDescList(List<DisableReviewRpt01FamChkfileDataCase> famChkfileDescList) {
        this.famChkfileDescList = famChkfileDescList;
    }

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
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

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
    }

    public BigDecimal getStudDataCount() {
        return studDataCount;
    }

    public void setStudDataCount(BigDecimal studDataCount) {
        this.studDataCount = studDataCount;
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

    public String getRepealInterDictDate() {
        return repealInterDictDate;
    }

    public void setRepealInterDictDate(String repealInterDictDate) {
        this.repealInterDictDate = repealInterDictDate;
    }

	public String getAbleApsYm() {
		return ableApsYm;
	}

	public void setAbleApsYm(String ableApsYm) {
		this.ableApsYm = ableApsYm;
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
