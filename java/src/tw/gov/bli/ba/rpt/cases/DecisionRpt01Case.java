package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 歸檔清單
 * 
 * @author jerry
 */
public class DecisionRpt01Case implements Serializable {
    // 給付主檔 (BAAPPBASE)
    // [
    private String caseTyp; // 案件類別
    private String apNo; // 受理編號
    private String evtName; // 事故者姓名
    private String issuYm; // 核定年月
    private String chkMan; // 審核人員
    private String exeMan; // 決行人員
    private String exeDate;//決行日期
    // ]
    
    private String note;// 備註
    private BigDecimal arcPg; // 歸檔頁次
    private BigDecimal baarclistNum;//歸檔序號
    
    /**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.isBlank(apNo))
            return "";

        if (StringUtils.equalsIgnoreCase(apNo.substring(0, 1), "L"))
            return "老年年金";
        else if (StringUtils.equalsIgnoreCase(apNo.substring(0, 1), "K"))
            return "失能年金";
        else if (StringUtils.equalsIgnoreCase(apNo.substring(0, 1), "S"))
            return "遺屬年金";
        else
            return "";
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
     * 核定年月(格式為: YYY 年 MM 月 
     * 
     * @return
     */
    public String getIssuYmString() {
        if (StringUtils.length(issuYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), true);
        else
            return StringUtils.defaultString(issuYm);
    }

    // /**
    // * 決行日期 (格式為: YYY 年 MM 月 DD 日)
    // *
    // * @return
    // */
    // public String getExeDateString() {
    // if (StringUtils.length(exeDate) == 8)
    // return DateUtility.formatChineseDateString(DateUtility.changeDateType(exeDate), true); // 中文年月日
    // else
    // return StringUtils.defaultString(exeDate);
    // }

    public DecisionRpt01Case() {

    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getChkMan() {
        return chkMan;
    }

    public void setChkMan(String chkMan) {
        this.chkMan = chkMan;
    }

    public BigDecimal getArcPg() {
        return arcPg;
    }

    public void setArcPg(BigDecimal arcPg) {
        this.arcPg = arcPg;
    }

    public BigDecimal getBaarclistNum() {
        return baarclistNum;
    }

    public void setBaarclistNum(BigDecimal baarclistNum) {
        this.baarclistNum = baarclistNum;
    }

    public String getCaseTyp() {
        return caseTyp;
    }

    public void setCaseTyp(String caseTyp) {
        this.caseTyp = caseTyp;
    }

    public String getNote() {
        if (StringUtils.length(note) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(note), false);
        else
            return StringUtils.defaultString(note);     
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getExeMan() {
        return exeMan;
    }

    public void setExeMan(String exeMan) {
        this.exeMan = exeMan;
    }

    public String getExeDate() {
        return exeDate;
    }

    public void setExeDate(String exeDate) {
        this.exeDate = exeDate;
    }

}
