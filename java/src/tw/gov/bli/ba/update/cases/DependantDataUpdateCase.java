package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baapplog;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewExtCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.util.DateUtil;

/**
 * Case for 眷屬資料更正
 * 
 * @author Evelyn Hsu
 */

public class DependantDataUpdateCase implements Serializable {
    
    private BigDecimal bafamilyId; // 資料列編號
    private BigDecimal baappbaseId; // 給付主檔資料編號
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String famEvtRel; // 眷屬與事故者關係
    private String famName; // 眷屬姓名
    private String famIdnNo; // 眷屬身分證號
    private String famBrDate; // 眷屬出生日期
    private String famAppDate; // 眷屬申請日期
    private String famDieDate; // 眷屬死亡日期
    private String famNationTyp; // 眷屬國籍別
    private String famSex; // 眷屬性別
    private String famNationCode; // 眷屬國籍
    private String raiseChildMk; // 配偶扶養
    private String handIcapMk; // 領有重度以上身心障礙手冊或證明
    private String abanApplyMk; // 放棄請領
    private String abanApsYm; // 放棄請領起始年月
    private String interDictMk; // 受禁治產宣告註記
    private String interDictDate; // 受禁治產宣告日期
    private String repealInterDictDate;//禁治產撤消日期
    private String relatChgDate; // 親屬關係變動日期
    private String adoPtDate; // 收養日期
    private String benMissingSdate; // 受益人失蹤期間(起)
    private String benMissingEdate; // 受益人失蹤期間(迄)
    private String prisonSdate; // 監管期間(起)
    private String prisonEdate; // 監管期間(迄)
    private String marryDate; // 結婚日期
    private String divorceDate; // 離婚日期
    private String studMk; // 在學
    private String studSdate; // 在學起始年月
    private String studEdate; // 在學結束年月
    private String monIncomeMk; // 每月工作收入註記
    private BigDecimal monIncome; // 每月工作收入
    private String closeDate; // 結案日期
    private String closeCause; // 結案原因
    private String delMk;//刪除註記
    private String idnChkYm;//身分查核年月
    private String idnChkNote; //身分查核年月註記
    
    private String evtName; // 事故者姓名 
    private String evtIdnNo; // 事故者身分證號
    private String evtBrDate; // 事故者出生日期
    private String appDate;// 申請日期
    private String evtDieDate; //事故者死亡日期
    private String evtJobDate; //事故日期
    private String ableApsYm; //得請領起月
    private String compelMk; //強迫不合格註記
    
    private String schoolCode; //學校代碼
    
    private List<DisabledPaymentReviewExtCase> otherChkDataList;// 眷屬編審註記/符合註記
    
    private List<Baapplog> baapplogList;// 更正記錄檔

    public List<Baapplog> getBaapplogList() {
        return baapplogList;
    }

    public void setBaapplogList(List<Baapplog> baapplogList) {
        this.baapplogList = baapplogList;
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
    
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1) + "-" + apNo.substring(1, 2) + "-" + apNo.substring(2, 7) + "-" + apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    
    
    public String getApNo1(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(0, 1);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNo2(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(1, 2);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNo3(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(2, 7);
        else
            return StringUtils.defaultString(apNo);
    }
    
    public String getApNo4(){
        if (StringUtils.length(apNo) == ConstantKey.APNO_LENGTH)
            return apNo.substring(7, 12);
        else
            return StringUtils.defaultString(apNo);
    }
    
    /**
     * 申請日期
     * 
     * @return
     */
    public String getAppDateStr() {
        if (StringUtils.isNotBlank(getAppDate())) {
            return DateUtility.changeDateType(getAppDate());
        }
        else {
            return "";
        }
    }
    
    public String getAppDateChineseString() {
        return DateUtil.changeDateType(appDate);
    }
    
    /**
     * 事故日期
     *  
     * @return
     */
    public String getEvtJobDateStr() {
        if (StringUtils.length(evtJobDate) == 8) {
           
            return DateUtility.changeDateType(evtJobDate);
        }
        else {
            return StringUtils.defaultString(evtJobDate);
        }
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
    
    public String getEvtBrDateChineseString() {
        if (StringUtils.length(evtBrDate) == 8) {
            return DateUtil.changeDateType(evtBrDate);
        }
        else {
                return StringUtils.defaultString(evtBrDate);
            }
    }
    
    /**
     * 眷屬出生日期
     * 
     * @return
     */
    public String getFamBrDateStr() {
        if (StringUtils.length(famBrDate) == 8) {
           
            return DateUtility.changeDateType(famBrDate);
        }
        else {
            return StringUtils.defaultString(famBrDate);
        }
    }
    
    /**
     * 事故者死亡日期
     * 
     * @return
     */
    public String getEvtDieDateStr() {
        if (StringUtils.length(evtDieDate) == 8) {
           
            return DateUtility.changeDateType(evtDieDate);
        }
        else {
            return StringUtils.defaultString(evtDieDate);
        }
    }
    
    public String getPayCode() {
        if (apNo.substring(0, 1).equals("L"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        else if (apNo.substring(0, 1).equals("K"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        else if (apNo.substring(0, 1).equals("S"))
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        else
            return "";
    }
    
    public String getFamEvtRelName() {
        if (famEvtRel.equals("1"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        else if (famEvtRel.equals("2"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        else if (famEvtRel.equals("3"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        else if (famEvtRel.equals("4"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        else if (famEvtRel.equals("5"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        else if (famEvtRel.equals("6"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        else if (famEvtRel.equals("7"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        else if (famEvtRel.equals("A"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        else if (famEvtRel.equals("C"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        else if (famEvtRel.equals("E"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        else if (famEvtRel.equals("F"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        else if (famEvtRel.equals("N"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        else if (famEvtRel.equals("Z"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        else if (famEvtRel.equals("O"))
            return ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        else
            return "";
    }
   

    
    public DependantDataUpdateCase(){
        
    }

    public BigDecimal getBafamilyId() {
        return bafamilyId;
    }

    public void setBafamilyId(BigDecimal bafamilyId) {
        this.bafamilyId = bafamilyId;
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

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
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

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

   
    public String getFamAppDate() {
        return famAppDate;
    }

    public void setFamAppDate(String famAppDate) {
        this.famAppDate = famAppDate;
    }

    public String getFamDieDate() {
        return famDieDate;
    }

    public void setFamDieDate(String famDieDate) {
        this.famDieDate = famDieDate;
    }

    public String getFamNationTyp() {
        return famNationTyp;
    }

    public void setFamNationTyp(String famNationTyp) {
        this.famNationTyp = famNationTyp;
    }

    public String getFamSex() {
        return famSex;
    }

    public void setFamSex(String famSex) {
        this.famSex = famSex;
    }

    public String getFamNationCode() {
        return famNationCode;
    }

    public void setFamNationCode(String famNationCode) {
        this.famNationCode = famNationCode;
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

    public String getAbanApplyMk() {
        return abanApplyMk;
    }

    public void setAbanApplyMk(String abanApplyMk) {
        this.abanApplyMk = abanApplyMk;
    }

    public String getAbanApsYm() {
        return abanApsYm;
    }

    public void setAbanApsYm(String abanApsYm) {
        this.abanApsYm = abanApsYm;
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

    public String getDelMk() {
        return delMk;
    }

    public void setDelMk(String delMk) {
        this.delMk = delMk;
    }

    public String getEvtDieDate() {
        return evtDieDate;
    }

    public void setEvtDieDate(String evtDieDate) {
        this.evtDieDate = evtDieDate;
    }

    public String getEvtJobDate() {
        return evtJobDate;
    }

    public void setEvtJobDate(String evtJobDate) {
        this.evtJobDate = evtJobDate;
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

    public String getRepealInterDictDate() {
        return repealInterDictDate;
    }

    public void setRepealInterDictDate(String repealInterDictDate) {
        this.repealInterDictDate = repealInterDictDate;
    }
    
    public String getCompelMk() {
        return compelMk;
    }

    public void setCompelMk(String compelMk) {
        this.compelMk = compelMk;
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

}
