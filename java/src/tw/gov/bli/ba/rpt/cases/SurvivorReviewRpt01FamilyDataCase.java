package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 眷屬資料
 * 
 * @author Rickychi
 */
public class SurvivorReviewRpt01FamilyDataCase implements Serializable {
    private String famName; // 眷屬姓名
    private String famAppDate; // 眷屬申請日期
    private String famIdnNo; // 眷屬身分證號
    private String famBrDate; // 眷屬出生日期
    private String famEvtRel; // 眷屬與事故者關係
    private String marryDate; // 結婚日期
    private String divorceDate; // 離婚日期
    private String monIncomeMk; // 每月工作收入註記
    private String monIncome; // 每月工作收入
    private String studMk; // 在學
    private String adoPtDate; // 收養日期
    private String raiseChildMk; // 配偶扶養
    private String abanApsYm; // 放棄請領起始年月
    private String relatChgDate; // 親屬關係變動日期
    private String benMissingSdate; // 受益人失蹤期間(起)
    private String benMissingEdate; // 受益人失蹤期間(迄)
    private String prisonSdate; // 監管期間(起)
    private String prisonEdate; // 監管期間(迄)
    private String interDictDate; // 受禁治產宣告日期
    private String famDieDate; // 眷屬死亡日期

    // 眷屬編審註記資料
    // [
    private List<SurvivorReviewRpt01ChkfileDataCase> chkfileDataList;

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
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

    public String getFamBrDate() {
        return famBrDate;
    }

    public void setFamBrDate(String famBrDate) {
        this.famBrDate = famBrDate;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
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

    public String getMonIncomeMk() {
        return monIncomeMk;
    }

    public void setMonIncomeMk(String monIncomeMk) {
        this.monIncomeMk = monIncomeMk;
    }

    public String getMonIncome() {
        return monIncome;
    }

    public void setMonIncome(String monIncome) {
        this.monIncome = monIncome;
    }

    public String getStudMk() {
        return studMk;
    }

    public void setStudMk(String studMk) {
        this.studMk = studMk;
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

    public String getFamDieDate() {
        return famDieDate;
    }

    public void setFamDieDate(String famDieDate) {
        this.famDieDate = famDieDate;
    }

    public List<SurvivorReviewRpt01ChkfileDataCase> getChkfileDataList() {
        return chkfileDataList;
    }

    public void setChkfileDataList(List<SurvivorReviewRpt01ChkfileDataCase> chkfileDataList) {
        this.chkfileDataList = chkfileDataList;
    }

}
