package tw.gov.bli.ba.bj.cases;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.util.DateUtility;

/**
 * case for 轉催收作業 Master
 * 
 * @author Rickychi
 */
public class Trans2OverdueReceivableBJMasterCase implements Serializable {
    private static final long serialVersionUID = 4482110121235865214L;
    private String unacpSdate;// 應收立帳起日
    private String unacpEdate;// 應收立帳迄日
    private BigDecimal totalAmt;// 應收總筆數
    private BigDecimal sumRecAmt;// 應收總金額

    // 頁面顯示轉換
    // [
    public String getUnacpSdateStr() {
        if (StringUtils.isNotBlank(getUnacpSdate())) {
            return DateUtility.changeDateType(getUnacpSdate());
        }
        else {
            return "";
        }
    }

    public String getUnacpEdateStr() {
        if (StringUtils.isNotBlank(getUnacpEdate())) {
            return DateUtility.changeDateType(getUnacpEdate());
        }
        else {
            return "";
        }
    }

    // ]
    public String getUnacpSdate() {
        return unacpSdate;
    }

    public void setUnacpSdate(String unacpSdate) {
        this.unacpSdate = unacpSdate;
    }

    public String getUnacpEdate() {
        return unacpEdate;
    }

    public void setUnacpEdate(String unacpEdate) {
        this.unacpEdate = unacpEdate;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getSumRecAmt() {
        return sumRecAmt;
    }

    public void setSumRecAmt(BigDecimal sumRecAmt) {
        this.sumRecAmt = sumRecAmt;
    }
}
