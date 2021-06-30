package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 歸檔清單補印
 * 
 * @author Jerry
 */
public class DecisionRpt02Case implements Serializable {
    private BigDecimal baarclistNum;// 序號
    private String payCode;// 給付別
    private String caseTyp; // 案件類別
    private String issuYm; // 核定年月
    private String exeBdate;// 決行日期起
    private String exeEdate;// 決行日期迄
    private BigDecimal arcPg;// 歸檔頁次
    private String arcDate;// 歸檔日期(印表日期)
    private String apNo; // 受理編號
    private String evtName; // 事故者姓名
    private String chkMan; // 審核人員
    private String exeMan; // 決行人員
    private String delMl;// 刪除註記
    private String note;// 備註

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

    /**
     * 決行日期起 (格式為: YYY 年 MM 月 DD 日)
     * 
     * @return
     */
    public String getExeBdateString() {
        if (StringUtils.length(exeBdate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(exeBdate), true); // 中文年月日
        else
            return StringUtils.defaultString(exeBdate);
    }

    /**
     * 決行日期起 (格式為: YYY 年 MM 月 DD 日)
     * 
     * @return
     */
    public String getExeEdateString() {
        if (StringUtils.length(exeEdate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(exeEdate), true); // 中文年月日
        else
            return StringUtils.defaultString(exeEdate);
    }

    /**
     * 歸檔日期(印表日期)(格式為: YYY 年 MM 月 DD 日)
     * 
     * @return
     */
    public String getArcDateString() {
        if (StringUtils.length(arcDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(arcDate), true); // 中文年月日
        else
            return StringUtils.defaultString(arcDate);
    }

    public DecisionRpt02Case() {

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

    public String getArcPgStr() {
        if (arcPg == null) {
            return "";
        }
        else {
            return arcPg.toString();
        }
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
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getExeBdate() {
        return exeBdate;
    }

    public void setExeBdate(String exeBdate) {
        this.exeBdate = exeBdate;
    }

    public String getExeEdate() {
        return exeEdate;
    }

    public void setExeEdate(String exeEdate) {
        this.exeEdate = exeEdate;
    }

    public String getArcDate() {
        return arcDate;
    }

    public void setArcDate(String arcDate) {
        this.arcDate = arcDate;
    }

    public String getDelMl() {
        return delMl;
    }

    public void setDelMl(String delMl) {
        this.delMl = delMl;
    }

    public String getReportNote() {
          return StringUtils.trimToEmpty(delMl)+"  "+StringUtils.trimToEmpty(note);
    }

    public String getExeMan() {
        return exeMan;
    }

    public void setExeMan(String exeMan) {
        this.exeMan = exeMan;
    }

}
