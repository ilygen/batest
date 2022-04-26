package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 災保老年年金給付受理編審清單 - 請領他類給付資料 - 申請死亡給付記錄
 * 
 * @author Goston
 */
public class DisasterInsuranceReviewRpt01DiePayDataCase implements Serializable {
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
    private String bmPayYm; // 給付年月
    private String bmLosFmDte; //申請起日
    private String bmLosToDte; //申請迄日
    private String bmEvType; //傷病分類
    private String bmDeaapItem; //申請項目

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

    public DisasterInsuranceReviewRpt01DiePayDataCase() {

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

    public String getBmPayYm() {
        return bmPayYm;
    }

    public void setBmPayYm(String bmPayYm) {
        this.bmPayYm = bmPayYm;
    }

	/**
	 * @return the bmEvType
	 */
	public String getBmEvType() {
		return bmEvType;
	}

	/**
	 * @param bmEvType the bmEvType to set
	 */
	public void setBmEvType(String bmEvType) {
		this.bmEvType = bmEvType;
	}

	/**
	 * @return the bmDeaapItem
	 */
	public String getBmDeaapItem() {
		return bmDeaapItem;
	}

	/**
	 * @param bmDeaapItem the bmDeaapItem to set
	 */
	public void setBmDeaapItem(String bmDeaapItem) {
		this.bmDeaapItem = bmDeaapItem;
	}

	/**
	 * @return the bmLosFmDte
	 */
	public String getBmLosFmDte() {
		return bmLosFmDte;
	}

	/**
	 * @param bmLosFmDte the bmLosFmDte to set
	 */
	public void setBmLosFmDte(String bmLosFmDte) {
		this.bmLosFmDte = bmLosFmDte;
	}

	/**
	 * @return the bmLosToDte
	 */
	public String getBmLosToDte() {
		return bmLosToDte;
	}

	/**
	 * @param bmLosToDte the bmLosToDte to set
	 */
	public void setBmLosToDte(String bmLosToDte) {
		this.bmLosToDte = bmLosToDte;
	}

    /**
     * 申請起日
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
     * 申請迄日
     * 
     * @return
     */
    public String getBmLosFmDteString() {
        if (StringUtils.length(bmLosFmDte) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(bmLosFmDte), false);
        else
            return StringUtils.defaultString(bmLosFmDte);
    }

}
