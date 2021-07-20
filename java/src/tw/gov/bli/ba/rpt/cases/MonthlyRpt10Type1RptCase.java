package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 核付清單
 * 
 * @author Goston
 */
public class MonthlyRpt10Type1RptCase implements Serializable {
    private String payCode; // 給付別

    private String apNo; // 受理編號
    private String issuYm; // 核定年月
    private String payYm; // 給付年月
    private String benIdn; // 受款人身份證號
    private String benName; // 受款人姓名
    private BigDecimal payAmt; // 金額
    private String chkDate; // 核定日期
    private String payDate; // 開票日期
    private String payTyp; // 給付方式
    private BigDecimal rptPage; // 頁次
    private String benEvtRel; // 受益人與事故者關係
    private String compName1; // 相關單位名稱1
    private String compName2; // 相關單位名稱2
    private String payBankId; // 入帳銀行 (總行)
    private String branchId; // 入帳銀行 (分行)
    private String payEeacc; // 入帳帳號
    
    private String accountNo; // 帳號
    private String payTypName; // 給付方式中文名稱
    private String cPrnDate; //印表日期

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
      * 取得入帳銀行
      * 
      * @return
      */
     public String getBankString() {
     	String bankString = "";
     	
     	payBankId = (StringUtils.isNotBlank(payBankId) ? payBankId : "");
     	branchId = (StringUtils.isNotBlank(branchId) ? branchId : "");
     	payEeacc = (StringUtils.isNotBlank(payEeacc) ? payEeacc : "");
     	
     	if(payTyp.equals("1") && !payBankId.equals("700")){
     		if(StringUtils.isBlank(payBankId) && StringUtils.isBlank(payEeacc)){
     		    bankString = "";
     		}else{
     			bankString = StringUtils.defaultString(payBankId) + "-0000-" + StringUtils.defaultString(payEeacc);
      		}
     	}else{
     		bankString = StringUtils.defaultString(payBankId) +"-"+ StringUtils.defaultString(branchId) +"-"+ StringUtils.defaultString(payEeacc);
     	}
         return bankString;
     }

    /**
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "老年";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "失能";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "遺屬";
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
     * 核定年月
     * 
     * @return
     */
    public String getIssuYmString() {
        if (StringUtils.length(issuYm) == 6)
            return DateUtility.changeWestYearMonthType(issuYm);
        else
            return StringUtils.defaultString(issuYm);
    }

    /**
     * 給付年月
     * 
     * @return
     */
    public String getPayYmString() {
        if (StringUtils.length(payYm) == 6)
            return DateUtility.changeWestYearMonthType(payYm);
        else
            return StringUtils.defaultString(payYm);
    }

    /**
     * 核定日期
     * 
     * @return
     */
    public String getChkDateString() {
        if (StringUtils.length(chkDate) == 8)
            return DateUtility.changeDateType(chkDate);
        else
            return StringUtils.defaultString(chkDate);
    }

    /**
     * 開票日期
     * 
     * @return
     */
    public String getPayDateString() {
        if (StringUtils.length(payDate) == 8)
            return DateUtility.formatChineseDateString(DateUtility.changeDateType(payDate), true);
        else
            return StringUtils.defaultString(payDate);
    }

    public MonthlyRpt10Type1RptCase() {

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

    public String getIssuYm() {
        return issuYm;
    }

    public void setIssuYm(String issuYm) {
        this.issuYm = issuYm;
    }

    public String getPayYm() {
        return payYm;
    }

    public void setPayYm(String payYm) {
        this.payYm = payYm;
    }

    public String getBenIdn() {
        return benIdn;
    }

    public void setBenIdn(String benIdn) {
        this.benIdn = benIdn;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayTyp() {
        return payTyp;
    }

    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }

    public BigDecimal getRptPage() {
        return rptPage;
    }

    public void setRptPage(BigDecimal rptPage) {
        this.rptPage = rptPage;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPayTypName() {
        return payTypName;
    }

    public void setPayTypName(String payTypName) {
        this.payTypName = payTypName;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public String getCompName1() {
        return compName1;
    }

    public void setCompName1(String compName1) {
        this.compName1 = compName1;
    }

    public String getCompName2() {
        return compName2;
    }

    public void setCompName2(String compName2) {
        this.compName2 = compName2;
    }


	public String getcPrnDate() {
		return cPrnDate;
	}


	public void setcPrnDate(String cPrnDate) {
		this.cPrnDate = cPrnDate;
	}


	public String getPayBankId() {
		return payBankId;
	}


	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}


	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}


	public String getPayEeacc() {
		return payEeacc;
	}


	public void setPayEeacc(String payEeacc) {
		this.payEeacc = payEeacc;
	}

}
