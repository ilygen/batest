package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Case for 代扣補償金清冊
 * 
 * @author Kiyomi
 */
public class MonthlyRpt24PayAmtCase implements Serializable {
    private String compName1; // 相關單位名稱1
    private BigDecimal payAmt; // 金額
    
    private String payBankId; // 入帳銀行 (總行)
    private String branchId; // 入帳銀行 (分行)
    private String payEeacc; // 入帳帳號    
    private String payTyp; //給付方式    

    public MonthlyRpt24PayAmtCase() {

    }

    public String getCompName1() {
        return compName1;
    }

    public void setCompName1(String compName1) {
        this.compName1 = compName1;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
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
    
    public String getPayTyp() {
        return payTyp;
    }
    
    public void setPayTyp(String payTyp) {
        this.payTyp = payTyp;
    }     

}
