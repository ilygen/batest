package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 請領他類給付資料 - 申請傷病給付記錄
 * 
 * @author Rickychi
 */
public class SurvivorReviewRpt01InjuryPayDataCase implements Serializable {
    private String bmApNo; // 受理號碼
    private String bmApDte; // 受理日期
    private String bmEvName; // 事故者姓名
    private BigDecimal bmChkAmt; // 核定金額
    private String bmExmDte; // 核定日期
    private String bmPayDte; // 核付日期
    private String bmNrepDate; // 補件日期
    private String bmNdocMk; // 補件註記
    private String bmNopDate; // 不給付日期
    private String bmEvtDte; // 事故日期
    private String bmLosFmDte; // 核定起日
    private String bmLosToDte; // 核定訖日
    private String bmPayYm; // 給付年月
    private String bmUbNo; // 保險證號
    private String bmEvCode; // 傷病原因
    private String bmInjInPart; // 受傷部位
    private String bmInjPfmDte; // 核定起日
    private String bmInjPtoDte; // 核定訖日
    private String bmEvType;//傷病分類
    private String bmCriInjNme; //傷病名稱
    private BigDecimal bmChkDay; // 核定日(月)數

    /**
     * 受理號碼 14 碼<br>
     * 格式: xxx-x-xx-xxxxxx-xx
     * 
     * @return
     */
    public String getBmApNoString() {
        this.bmApNo = StringUtils.rightPad(bmApNo, 14, " ");
        return bmApNo.substring(0, 3) + "-" + bmApNo.substring(3, 4) + "-" + bmApNo.substring(4, 6) + "-" + bmApNo.substring(6, 12) + "-" + bmApNo.substring(12, 14);
    }
    
    /**
     * 核定起日
     * 
     * @return
     */
    public String getBmInjPfmDteString() {
        if (StringUtils.length(bmInjPfmDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmInjPfmDte), false);
        else
            return StringUtils.defaultString(bmInjPfmDte);
    }
    
    /**
     * 核定迄日
     * 
     * @return
     */
    public String getBmInjPtoDteString() {
        if (StringUtils.length(bmInjPtoDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmInjPtoDte), false);
        else
            return StringUtils.defaultString(bmInjPtoDte);
    }

    /**
     * 受理日期
     * 
     * @return
     */
    public String getBmApDteString() {
        if (StringUtils.length(bmApDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmApDte), false);
        else
            return StringUtils.defaultString(bmApDte);
    }

    /**
     * 核定日期
     * 
     * @return
     */
    public String getBmExmDteString() {
        if (StringUtils.length(bmExmDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmExmDte), false);
        else
            return StringUtils.defaultString(bmExmDte);
    }

    /**
     * 核付日期
     * 
     * @return
     */
    public String getBmPayDteString() {
        if (StringUtils.length(bmPayDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmPayDte), false);
        else
            return StringUtils.defaultString(bmPayDte);
    }

    /**
     * 補件日期
     * 
     * @return
     */
    public String getBmNrepDateString() {
        if (StringUtils.length(bmNrepDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmNrepDate), false);
        else
            return StringUtils.defaultString(bmNrepDate);
    }

    /**
     * 不給付日期
     * 
     * @return
     */
    public String getBmNopDateString() {
        if (StringUtils.length(bmNopDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmNopDate), false);
        else
            return StringUtils.defaultString(bmNopDate);
    }

    /**
     * 事故日期
     * 
     * @return
     */
    public String getBmEvtDteString() {
        if (StringUtils.length(bmEvtDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmEvtDte), false);
        else
            return StringUtils.defaultString(bmEvtDte);
    }

    /**
     * 核定起日
     * 
     * @return
     */
    public String getBmLosFmDteString() {
        if (StringUtils.length(bmLosFmDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmLosFmDte), false);
        else
            return StringUtils.defaultString(bmLosFmDte);
    }

    /**
     * 核定訖日
     * 
     * @return
     */
    public String getBmLosToDteString() {
        if (StringUtils.length(bmLosToDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmLosToDte), false);
        else
            return StringUtils.defaultString(bmLosToDte);
    }

    /**
     * 給付年月
     * 
     * @return
     */
    public String getBmPayYmString() {
        if (StringUtils.length(bmPayYm) == 6)
            return DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(bmPayYm), false);
        else
            return StringUtils.defaultString(bmPayYm);
    }

    public SurvivorReviewRpt01InjuryPayDataCase() {
    }

    public String getBmApNo() {
        return bmApNo;
    }

    public void setBmApNo(String bmApNo) {
        this.bmApNo = bmApNo;
    }

    public String getBmApDte() {
        return bmApDte;
    }

    public void setBmApDte(String bmApDte) {
        this.bmApDte = bmApDte;
    }

    public String getBmEvName() {
        return bmEvName;
    }

    public void setBmEvName(String bmEvName) {
        this.bmEvName = bmEvName;
    }

    public BigDecimal getBmChkAmt() {
        return bmChkAmt;
    }

    public void setBmChkAmt(BigDecimal bmChkAmt) {
        this.bmChkAmt = bmChkAmt;
    }

    public String getBmExmDte() {
        return bmExmDte;
    }

    public void setBmExmDte(String bmExmDte) {
        this.bmExmDte = bmExmDte;
    }

    public String getBmPayDte() {
        return bmPayDte;
    }

    public void setBmPayDte(String bmPayDte) {
        this.bmPayDte = bmPayDte;
    }

    public String getBmNrepDate() {
        return bmNrepDate;
    }

    public void setBmNrepDate(String bmNrepDate) {
        this.bmNrepDate = bmNrepDate;
    }

    public String getBmNdocMk() {
        return bmNdocMk;
    }

    public void setBmNdocMk(String bmNdocMk) {
        this.bmNdocMk = bmNdocMk;
    }

    public String getBmNopDate() {
        return bmNopDate;
    }

    public void setBmNopDate(String bmNopDate) {
        this.bmNopDate = bmNopDate;
    }

    public String getBmEvtDte() {
        return bmEvtDte;
    }

    public void setBmEvtDte(String bmEvtDte) {
        this.bmEvtDte = bmEvtDte;
    }

    public String getBmLosFmDte() {
        return bmLosFmDte;
    }

    public void setBmLosFmDte(String bmLosFmDte) {
        this.bmLosFmDte = bmLosFmDte;
    }

    public String getBmLosToDte() {
        return bmLosToDte;
    }

    public void setBmLosToDte(String bmLosToDte) {
        this.bmLosToDte = bmLosToDte;
    }

    public String getBmPayYm() {
        return bmPayYm;
    }

    public void setBmPayYm(String bmPayYm) {
        this.bmPayYm = bmPayYm;
    }

    public String getBmUbNo() {
        return bmUbNo;
    }

    public void setBmUbNo(String bmUbNo) {
        this.bmUbNo = bmUbNo;
    }

    public String getBmEvCode() {
        return bmEvCode;
    }

    public void setBmEvCode(String bmEvCode) {
        this.bmEvCode = bmEvCode;
    }

    public String getBmInjInPart() {
        return bmInjInPart;
    }

    public void setBmInjInPart(String bmInjInPart) {
        this.bmInjInPart = bmInjInPart;
    }

    public String getBmInjPfmDte() {
        return bmInjPfmDte;
    }

    public void setBmInjPfmDte(String bmInjPfmDte) {
        this.bmInjPfmDte = bmInjPfmDte;
    }

    public String getBmInjPtoDte() {
        return bmInjPtoDte;
    }

    public void setBmInjPtoDte(String bmInjPtoDte) {
        this.bmInjPtoDte = bmInjPtoDte;
    }

	public String getBmEvType() {
		return bmEvType;
	}

	public void setBmEvType(String bmEvType) {
		this.bmEvType = bmEvType;
	}

	public String getBmCriInjNme() {
		return bmCriInjNme;
	}

	public void setBmCriInjNme(String bmCriInjNme) {
		this.bmCriInjNme = bmCriInjNme;
	}

	public BigDecimal getBmChkDay() {
		return bmChkDay;
	}

	public void setBmChkDay(BigDecimal bmChkDay) {
		this.bmChkDay = bmChkDay;
	}
    
    

}
