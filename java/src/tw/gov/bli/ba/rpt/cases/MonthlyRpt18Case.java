package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 退回現金轉暫收及待結轉清單
 * 
 * @author Goston
 */
public class MonthlyRpt18Case implements Serializable {
    private String payCode; // 給付別

    private String otherApNo1; // 他類案件受理編號 - 受理編號
    private String payDate; // 核付日期 - 退回日期
    private String benName; // 受益人姓名 - 受款人姓名
    private BigDecimal issueAmt; // 核定金額 - 金額
    private String issuTyp; // 核付分類 - 類別
    private String payTyp; // 給付方式
    private String cPrnDate; //印表日期
    
    //判斷給付別使用
    private String nlWkMk;    
    private String adWkMk;     
    private String naChgMk; 
    private String isNaChgMk;

    /**
         * 印表日期
         * 
         * @return
         */
     public String getcPrnDateStr() {
            if (StringUtils.isNotBlank(getcPrnDate()))
                return DateUtility.changeDateType(getcPrnDate());
            else
                return "";
     }

    /**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "普通-老年給付(年金)";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "○○-失能給付(年金)";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "○○-遺屬給付(年金)";
        else
            return "";
    }

    /**
     * 受理號碼 14 碼<br>
     * 格式: xxx-x-xx-xxxxxx-xx
     * 
     * @return
     */
    public String getOtherApNo1String() {
        if (StringUtils.isBlank(StringUtils.trimToEmpty(otherApNo1)))
            return "";

        if (StringUtils.length(StringUtils.trimToEmpty(otherApNo1)) == 12) {
            return otherApNo1.substring(0, 3) + "-" + otherApNo1.substring(3, 4) + "-" + otherApNo1.substring(4, 6) + "-" + otherApNo1.substring(6, 12);
        }
        else {
            String tempApNo = StringUtils.rightPad(otherApNo1, 14, " ");
            return tempApNo.substring(0, 3) + "-" + tempApNo.substring(3, 4) + "-" + tempApNo.substring(4, 6) + "-" + tempApNo.substring(6, 12) + "-" + tempApNo.substring(12, 14);
        }
    }

    /**
     * 退回日期
     * 
     * @return
     */
    public String getPayDateString() {
        if (StringUtils.length(payDate) == 8)
            return DateUtility.changeDateType(payDate);
        else
            return StringUtils.defaultString(payDate);
    }

    /**
     * 退回日期 (xxx 年 xx 月 xx 日)
     * 
     * @return
     */
    public String getPayDateChineseString() {
        if (StringUtils.length(payDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(payDate), true);
        else
            return StringUtils.defaultString(payDate);
    }

    /**
     * 類別
     * 
     * @return
     */
    public String getIssuTypString() {
        if (StringUtils.equalsIgnoreCase(issuTyp, ConstantKey.BAAPPBASE_CASETYP_1))
            return ConstantKey.BAAPPBASE_CASETYP_STR_1;
        else if (StringUtils.equalsIgnoreCase(issuTyp, ConstantKey.BAAPPBASE_CASETYP_2))
            return ConstantKey.BAAPPBASE_CASETYP_STR_2;
        else if (StringUtils.equalsIgnoreCase(issuTyp, ConstantKey.BAAPPBASE_CASETYP_3))
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        else if (StringUtils.equalsIgnoreCase(issuTyp, ConstantKey.BAAPPBASE_CASETYP_4))
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        else if (StringUtils.equalsIgnoreCase(issuTyp, ConstantKey.BAAPPBASE_CASETYP_5))
            return ConstantKey.BAAPPBASE_CASETYP_STR_5;
        else if (StringUtils.equalsIgnoreCase(issuTyp, ConstantKey.BAAPPBASE_CASETYP_6))
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        else
            return "";
    }

    /**
     * 給付方式
     * 
     * @return
     */
    public String getPayTypString() {
        if ((ConstantKey.BAAPPBASE_PAYTYP_1).equals(getPayTyp()))
            return "金融轉帳";// 原應為"匯入銀行帳號",此報表改為"金融轉帳"
        else if ((ConstantKey.BAAPPBASE_PAYTYP_2).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_2;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_3).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_3;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_4).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_4;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_5).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_5;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_6).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_6;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_7).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_7;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_8).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_8;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_9).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_9;
        else if ((ConstantKey.BAAPPBASE_PAYTYP_A).equals(getPayTyp()))
            return ConstantKey.BAAPPBASE_PAYTYP_STR_A;
        else if (("").equals(getPayTyp()))
            return "其它";
        else
            return "";
    }

    public MonthlyRpt18Case() {

    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getOtherApNo1() {
        return otherApNo1;
    }

    public void setOtherApNo1(String otherApNo1) {
        this.otherApNo1 = otherApNo1;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getIssuTyp() {
        return issuTyp;
    }

    public void setIssuTyp(String issuTyp) {
        this.issuTyp = issuTyp;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

	public String getcPrnDate() {
		return cPrnDate;
	}

	public void setcPrnDate(String cPrnDate) {
		this.cPrnDate = cPrnDate;
	}

	public String getNlWkMk() {
		return nlWkMk;
	}

	public void setNlWkMk(String nlWkMk) {
		this.nlWkMk = nlWkMk;
	}

	public String getAdWkMk() {
		return adWkMk;
	}

	public void setAdWkMk(String adWkMk) {
		this.adWkMk = adWkMk;
	}

	public String getNaChgMk() {
		return naChgMk;
	}

	public void setNaChgMk(String naChgMk) {
		this.naChgMk = naChgMk;
	}

	public String getIsNaChgMk() {
		return isNaChgMk;
	}

	public void setIsNaChgMk(String isNaChgMk) {
		this.isNaChgMk = isNaChgMk;
	}

}
