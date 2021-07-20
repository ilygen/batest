package tw.gov.bli.ba.query.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付查詢作業 眷屬資料
 * 
 * @author Rickychi
 */
public class PaymentQueryFamilyDataCase implements Serializable {
    private static final long serialVersionUID = 7711591357902386283L;
    private String apNo;// 受理編號
    private String seqNo;// 序號
    private String famAppDate;// 眷屬申請日期
    private String famIdnNo;// 眷屬身分證號
    private String famName;// 眷屬姓名
    private String famBrDate;// 眷屬出生日期
    private String famSex;// 眷屬性別
    private String famNationTyp;// 眷屬國籍別
    private String famNationCode;// 眷屬國籍
    private String famNationName;// 眷屬國籍名稱
    private String famEvtRel;// 眷屬與事故者關係
    private String famDieDate;// 眷屬死亡日期
    private String relatChgDate;// 親屬關係變動日期
    private String raiseChildMk;// 配偶扶養
    private String handIcapMk;// 領有重度以上身心障礙手冊或證明
    private String monIncomeMk;// 每月工作收入註記
    private BigDecimal monIncome;// 每月工作收入
    private String marryDate;// 結婚日期
    private String divorceDate;// 離婚日期
    private String abanApplyMk;// 放棄請領
    private String abanApsYm;// 放棄請領起始年月
    private String interDictMk;// 受禁治產(監護)宣告
    private String interDictDate;// 受禁治產(監護)宣告 - 宣告日期
    private String repealInterDictDate;// 受禁治產(監護)宣告 - 撤銷日期
    private String adoPtDate;// 收養日期
    private String benMissingSdate;// 受益人失蹤期間(起)
    private String benMissingEdate;// 受益人失蹤期間(迄)
    private String prisonSdate;// 監管期間(起)
    private String prisonEdate;// 監管期間(迄)
    private String studMk;// 在學
    private String studSdate;// 在學起始年月
    private String studEdate;// 在學結束年月
    private BigDecimal studDataCount;// 在學記錄筆數
    private String closeDate;// 結案日期
    private String closeCause;// 結案原因
    private String idnChkYm; // 身份查核年月
    private String issuPayYm;// 編審核定年月
    private Integer chkFileDataSize;// 資料筆數
    private List<PaymentQueryChkFileDataCase> chkFileDataList;// 編審註記資料

    private List<PaymentQueryFamilyDataCase> benChkFileDataList;// 眷屬編審註記資料
    private List<PaymentQueryFamilyDataCase> matchChkFileDataList;// 符合註記

    private String ableApsYm; // 得請領起月
    private String schoolCodeStr; // 學校代碼 中文

    // 頁面顯示轉換
    // [
    public String getApNoStrDisplay() {
        if (StringUtils.isNotBlank(getApNo()) && getApNo().length() == ConstantKey.APNO_LENGTH)
            return getApNo().substring(0, 1) + "-" + getApNo().substring(1, 2) + "-" + getApNo().substring(2, 7) + "-" + getApNo().substring(7, 12);
        else
            return "";
    }

    public String getFamAppDateStr() {
        if (StringUtils.isNotBlank(getFamAppDate())) {
            return DateUtility.changeDateType(getFamAppDate());
        }
        else {
            return getFamAppDate();
        }
    }

    public String getFamBrDateStr() {
        if (StringUtils.isNotBlank(getFamBrDate())) {
            return DateUtility.changeDateType(getFamBrDate());
        }
        else {
            return getFamBrDate();
        }
    }

    public String getFamDieDateStr() {
        if (StringUtils.isNotBlank(getFamDieDate())) {
            return DateUtility.changeDateType(getFamDieDate());
        }
        else {
            return getFamDieDate();
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

    public String getMarryDateStr() {
        if (StringUtils.isNotBlank(getMarryDate())) {
            return DateUtility.changeDateType(getMarryDate());
        }
        else {
            return getMarryDate();
        }
    }

    public String getDivorceDateStr() {
        if (StringUtils.isNotBlank(getDivorceDate())) {
            return DateUtility.changeDateType(getDivorceDate());
        }
        else {
            return getDivorceDate();
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

    public String getInterDictDateStr() {
        if (StringUtils.isNotBlank(getInterDictDate())) {
            return DateUtility.changeDateType(getInterDictDate());
        }
        else {
            return getInterDictDate();
        }
    }

    public String getRepealInterDictDateStr() {
        if (StringUtils.isNotBlank(getRepealInterDictDate())) {
            return DateUtility.changeDateType(getRepealInterDictDate());
        }
        else {
            return getRepealInterDictDate();
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

    public String getCloseDateStr() {
        if (StringUtils.isNotBlank(getCloseDate())) {
            return DateUtility.changeDateType(getCloseDate());
        }
        else {
            return getCloseDate();
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

    public String getFamSexStr() {
        String famSexStr = "";
        if (StringUtils.isNotBlank(getFamSex())) {
            // 轉換 眷屬性別
            if ((ConstantKey.BAAPPBASE_SEX_1).equals(getFamSex())) {
                famSexStr = ConstantKey.BAAPPBASE_SEX_STR_1;
            }
            else if ((ConstantKey.BAAPPBASE_SEX_2).equals(getFamSex())) {
                famSexStr = ConstantKey.BAAPPBASE_SEX_STR_2;
            }
        }
        return famSexStr;
    }

    public String getFamEvtRelStr() {
        String famEvtRelStr = "";
        if ((ConstantKey.BAAPPBASE_BENEVTREL_1).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_1;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_2).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_2;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_3).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_3;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_4).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_4;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_5).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_5;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_6).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_6;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_7).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_7;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_A).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_A;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_C).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_C;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_E).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_E;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_F).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_F;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_N).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_N;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_Z).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_Z;
        }
        else if ((ConstantKey.BAAPPBASE_BENEVTREL_O).equals(getFamEvtRel())) {
            famEvtRelStr = ConstantKey.BAAPPBASE_BENEVTREL_CODESTR_O;
        }
        return famEvtRelStr;
    }

    public String getFamNationTypStr() {
        String famNationTypStr = "";
        if (StringUtils.isNotBlank(getFamNationTyp())) {
            if ((ConstantKey.BAAPPBASE_NATIONCODE_1).equals(getFamNationTyp())) {
                famNationTypStr = ConstantKey.BAAPPBASE_NATIONCODE_STR_1;
            }
            else if ((ConstantKey.BAAPPBASE_NATIONCODE_2).equals(getFamNationTyp())) {
                famNationTypStr = ConstantKey.BAAPPBASE_NATIONCODE_STR_2;
            }
        }
        return famNationTypStr;
    }

    public String getMonIncomeMkStr() {
        String monIncomeMkStr = "";
        if (StringUtils.isNotBlank(getMonIncomeMk()) && ("Y").equals(getMonIncomeMk())) {
            monIncomeMkStr = "有";
        }
        return monIncomeMkStr;
    }

    public String getHandIcapMkStr() {
        String handIcapMkStr = "";
        if (StringUtils.isNotBlank(getHandIcapMk()) && ("Y").equals(getHandIcapMk())) {
            handIcapMkStr = "有";
        }
        return handIcapMkStr;
    }

    public String getRaiseChildMkStr() {
        String raiseChildMkStr = "";
        if (StringUtils.isNotBlank(getRaiseChildMk()) && ("Y").equals(getRaiseChildMk())) {
            raiseChildMkStr = "有";
        }
        return raiseChildMkStr;
    }

    public String getIdnChkYmStr() {
        if (StringUtils.isNotBlank(getIdnChkYm())) {
            return DateUtility.changeWestYearMonthType(getIdnChkYm());
        }
        else {
            return getIdnChkYm();
        }
    }

    // ]

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

    public String getHandIcapMk() {
        return handIcapMk;
    }

    public void setHandIcapMk(String handIcapMk) {
        this.handIcapMk = handIcapMk;
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

    public List<PaymentQueryFamilyDataCase> getBenChkFileDataList() {
        return benChkFileDataList;
    }

    public void setBenChkFileDataList(List<PaymentQueryFamilyDataCase> benChkFileDataList) {
        this.benChkFileDataList = benChkFileDataList;
    }

    public List<PaymentQueryFamilyDataCase> getMatchChkFileDataList() {
        return matchChkFileDataList;
    }

    public void setMatchChkFileDataList(List<PaymentQueryFamilyDataCase> matchChkFileDataList) {
        this.matchChkFileDataList = matchChkFileDataList;
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

    public List<PaymentQueryChkFileDataCase> getChkFileDataList() {
        return chkFileDataList;
    }

    public void setChkFileDataList(List<PaymentQueryChkFileDataCase> chkFileDataList) {
        this.chkFileDataList = chkFileDataList;
    }

    public String getFamNationName() {
        return famNationName;
    }

    public void setFamNationName(String famNationName) {
        this.famNationName = famNationName;
    }

    public BigDecimal getStudDataCount() {
        return studDataCount;
    }

    public void setStudDataCount(BigDecimal studDataCount) {
        this.studDataCount = studDataCount;
    }

    public String getIdnChkYm() {
        return idnChkYm;
    }

    public void setIdnChkYm(String idnChkYm) {
        this.idnChkYm = idnChkYm;
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

    public String getSchoolCodeStr() {
        return schoolCodeStr;
    }

    public void setSchoolCodeStr(String schoolCodeStr) {
        this.schoolCodeStr = schoolCodeStr;
    }

}
