package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 退回現金清冊
 * 
 * @author Goston
 */
public class MonthlyRpt17Case implements Serializable {
    private String payCode; // 給付別

    private String apNo; // 年金受理編號
    private String payYm; // 給付年月
    private String payDate; // 核付日期 - 憑證日期
    private String benName; // 受益人姓名
    private String benIdn; // 受益人身分證號
    private BigDecimal issueAmt; // 核定金額
    private String commTel; // 受益人聯絡電話
    private String otherApNo1; // 他類案件受理編號
    private String otherSeqNo1; // 他類案件受款人序號
    private String hjMk; // 移至註記
    private String issuTyp; // 核付分類 - 案件類別
    private String payTyp; // 給付方式
    private String dataKd; // 資料種類
    private String dataTyp; // 資料種類
    private String bliPayeeAcc; // 本局帳戶
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
     * 年金受理編號
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
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        return DateUtility.changeWestYearMonthType(payYm);
    }

    /**
     * 憑證日期
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
     * 案件類別
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

    public MonthlyRpt17Case() {

    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
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

    public String getBenIdn() {
        return benIdn;
    }

    public void setBenIdn(String benIdn) {
        this.benIdn = benIdn;
    }

    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    public String getCommTel() {
        return commTel;
    }

    public void setCommTel(String commTel) {
        this.commTel = commTel;
    }

    public String getOtherApNo1() {
        return otherApNo1;
    }

    public void setOtherApNo1(String otherApNo1) {
        this.otherApNo1 = otherApNo1;
    }

    public String getOtherSeqNo1() {
        return otherSeqNo1;
    }

    public void setOtherSeqNo1(String otherSeqNo1) {
        this.otherSeqNo1 = otherSeqNo1;
    }

    public String getHjMk() {
        return hjMk;
    }

    public void setHjMk(String hjMk) {
        this.hjMk = hjMk;
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

    public String getDataKd() {
        return dataKd;
    }

    public void setDataKd(String dataKd) {
        this.dataKd = dataKd;
    }

    public String getDataTyp() {
        return dataTyp;
    }

    public void setDataTyp(String dataTyp) {
        this.dataTyp = dataTyp;
    }

    public String getBliPayeeAcc() {
        return bliPayeeAcc;
    }

    public void setBliPayeeAcc(String bliPayeeAcc) {
        this.bliPayeeAcc = bliPayeeAcc;
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
