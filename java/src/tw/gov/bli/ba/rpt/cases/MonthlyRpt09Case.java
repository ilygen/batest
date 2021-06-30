package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * Case for 給付抵銷紓困貸款明細
 * 
 * @author Goston
 */
public class MonthlyRpt09Case implements Serializable {
    private String payCode; // 給付別

    private String branchId; // 分支代號
    private String payEeacc; // 銀行帳號 - 放款帳號
    private String benName; // 受益人姓名 - 姓名
    private String benIdn; // 受益人身分證號 - 身分證號
    private BigDecimal payAmt; // 實付金額 - 抵銷金額
    private String dlineDate; // 勞貸本息截止日 - 本息截止日
    private String payDate; // 核付日期
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
     * 給付別中文名稱
     * 
     * @return
     */
    public String getPayCodeString() {
        if (StringUtils.equalsIgnoreCase(payCode, "L"))
            return "老年年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "K"))
            return "失能年金";
        else if (StringUtils.equalsIgnoreCase(payCode, "S"))
            return "遺屬年金";
        else
            return "";
    }

    /**
     * 抵銷金額
     * 
     * @return
     */
    public BigDecimal getPayAmtNonNull() {
        if (payAmt == null)
            return new BigDecimal(0);
        else
            return payAmt;
    }

    public MonthlyRpt09Case() {

    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
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

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getDlineDate() {
        return dlineDate;
    }

    public void setDlineDate(String dlineDate) {
        this.dlineDate = dlineDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

	public String getcPrnDate() {
		return cPrnDate;
	}

	public void setcPrnDate(String cPrnDate) {
		this.cPrnDate = cPrnDate;
	}

}
